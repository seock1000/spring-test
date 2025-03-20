package com.zuzu.dayonetest.service;

import com.zuzu.dayonetest.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisServiceTest extends IntegrationTest {

    @Autowired
    private RedisService redisService;

    @Test
    @DisplayName("redis get set 테스트")
    public void redisGetSetTest() {
        //given
        String expectedValue = "hello";
        String key = "hi";

        //when
        redisService.set(key, expectedValue);

        //then
        Assertions.assertEquals(expectedValue, redisService.get(key));
    }
}
