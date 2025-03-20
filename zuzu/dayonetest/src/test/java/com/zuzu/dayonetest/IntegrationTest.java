package com.zuzu.dayonetest;

import org.junit.Ignore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

// 다른 테스트 코드에 상속시킬 부모클래스로 무시
@Ignore
// 테스트 동작 이후 롤백 실행 -> db에는 데이터 저장 x
@Transactional
// Spring Bean을 모두 scan해서 test
@SpringBootTest
@ContextConfiguration(initializers = IntegrationTest.IntegrationInitializer.class)
public class IntegrationTest {

    static DockerComposeContainer rdbms;

    // 잘 실행하는지 로그 메세지의 표출 여부로 테스트
    static {
        rdbms = new DockerComposeContainer(new File("infra/test/docker-compose.yaml"))
                .withExposedService(
                        "local-db",
                        3306,
                        Wait.forLogMessage(".*ready for connections.*", 1)
                                .withStartupTimeout(Duration.ofSeconds(300))
                )
                .withExposedService(
                        "local-db-migrate",
                        0,
                        Wait.forLogMessage("(.*Successfully applied.*)|(.*Successfully validated.*)", 1)
                                .withStartupTimeout(Duration.ofSeconds(300))
                );

        rdbms.start();
    }

    static class IntegrationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        // test 환경 configure
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            Map<String, String> properties = new HashMap<>();

            var rdbmsHost = rdbms.getServiceHost("local-db", 3306);
            var rdbmsPort = rdbms.getServicePort("local-db", 3306);

            // 동적으로 property 적용
            properties.put("spring.datasource.url", "jdbc:mysql://" + rdbmsHost + ":" + rdbmsPort + "/score");

            TestPropertyValues.of(properties)
                    .applyTo(applicationContext);
        }
    }
}
