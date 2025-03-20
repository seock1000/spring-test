package com.zuzu.dayonetest;

import com.zuzu.dayonetest.model.StudentScoreFixture;
import com.zuzu.dayonetest.repository.StudentScoreRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class DayonetestApplicationTests extends IntegrationTest {

    @Autowired
    private StudentScoreRepository studentScoreRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void contextLoads() {
        var studentScore = StudentScoreFixture.passed();
        var savedStudentScore = studentScoreRepository.save(studentScore);

        entityManager.flush();
        entityManager.clear();

        var queryStudentScore = studentScoreRepository.findById(savedStudentScore.getId()).orElseThrow();

        Assertions.assertEquals(savedStudentScore.getId(), queryStudentScore.getId());
        Assertions.assertEquals(savedStudentScore.getStudentName(), queryStudentScore.getStudentName());
        Assertions.assertEquals(savedStudentScore.getKorScore(), queryStudentScore.getKorScore());
        Assertions.assertEquals(savedStudentScore.getEnglishScore(), queryStudentScore.getEnglishScore());
        Assertions.assertEquals(savedStudentScore.getMathScore(), queryStudentScore.getMathScore());
    }

}
