package com.zuzu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JunitPracticeTest {

    @Test
    public void assertEqualsTest() {
        String expected = "Something";
        String actual = "Something";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void assertNotEqualsTest() {
        String expected = "Something";
        String actual = "Hello";

        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    public void assertTrueTest() {
        Assertions.assertTrue(true);

        Integer a = 10;
        Integer b = 10;
        Assertions.assertTrue(a.equals(b));
    }

    @Test
    public void assertFalseTest() {
        Assertions.assertFalse(false);

        Integer a = 10;
        Integer b = 1;
        Assertions.assertFalse(a.equals(b));
    }

    @Test
    public void assertThrowTest() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException();
        });
    }

    @Test
    public void assertNotNullTest() {
        String value = "Hello";
        Assertions.assertNotNull(value);
    }

    @Test
    public void assertNull() {
        String value = null;
        Assertions.assertNull(value);
    }

    @Test
    public void assertIterableEquals() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertIterableEquals(list1, list2);
    }

    @Test
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
