package com.zuzu.dayonetest;

import org.junit.jupiter.api.*;

import java.util.List;

// DisplayNameGenerator.ReplaceUnderscores.class : under bar를 공백으로 변환
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JunitPracticeTest {

    @Test
    @DisplayName("Assert Equals 메소드 테스트")
    public void assertEqualsTest() {
        String expected = "Something";
        String actual = "Something";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Assert Not Equals 메소드 테스트")
    public void assertNotEqualsTest() {
        String expected = "Something";
        String actual = "Hello";

        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    @DisplayName("Assert True 메소드 테스트")
    public void assertTrueTest() {
        Assertions.assertTrue(true);

        Integer a = 10;
        Integer b = 10;
        Assertions.assertTrue(a.equals(b));
    }

    @Test
    @DisplayName("Assert False 메소드 테스트")
    public void assertFalseTest() {
        Assertions.assertFalse(false);

        Integer a = 10;
        Integer b = 1;
        Assertions.assertFalse(a.equals(b));
    }

    @Test
    @DisplayName("Assert Throw 메소드 테스트")
    public void assertThrowTest() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException();
        });
    }

    @Test
    @DisplayName("Assert Not Null 메소드 테스트")
    public void assertNotNullTest() {
        String value = "Hello";
        Assertions.assertNotNull(value);
    }

    @Test
    @DisplayName("Assert Null 메소드 테스트")
    public void assertNull() {
        String value = null;
        Assertions.assertNull(value);
    }

    @Test
    @DisplayName("Assert Iterable 메소드 테스트")
    public void assertIterableEquals() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertIterableEquals(list1, list2);
    }

    @Test
    @DisplayName("Assert All 메소드 테스트")
    public void assetAllTest() {

        String expected = "Something";
        String actual = "Something";

        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertAll("Assert All", List.of(
                () -> { Assertions.assertEquals(expected, actual); },
                () -> { Assertions.assertIterableEquals(list1, list2); }
        ));
    }
}
