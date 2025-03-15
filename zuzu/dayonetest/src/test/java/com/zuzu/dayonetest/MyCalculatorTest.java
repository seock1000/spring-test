package com.zuzu.dayonetest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyCalculatorTest {

    @Test
    void add() {
        // AAA 패턴

        // Arrange : 준비
        MyCalculator calculator = new MyCalculator();

        // Act : 행동
        calculator.add(10.0);

        // Assert : 단언/검증
        assertEquals(10.0, calculator.getResult());
    }

    @Test
    void minus() {
        //GWT 패턴

        // Given : 준비
        MyCalculator calculator = new MyCalculator(10.0);

        // When : 행동
        calculator.minus(5.0);

        // Then : 단언/검증
        assertEquals(5.0, calculator.getResult());
    }

    @Test
    void divide() {
        MyCalculator calculator = new MyCalculator(10.0);

        calculator.divide(2.0);

        assertEquals(5.0, calculator.getResult());
    }

    @Test
    void multiply() {
        MyCalculator calculator = new MyCalculator(2.0);

        calculator.multiply(2.0);

        assertEquals(4.0, calculator.getResult());
    }

    @Test
    void complicatedCalculateTest() {
        //given
        MyCalculator calculator = new MyCalculator(0.0);

        //when
        Double result = calculator
                .add(10.0)
                .minus(4.0)
                .multiply(2.0)
                .divide(3.0)
                .getResult();

        //then
        assertEquals(4.0, result);
    }

    @Test
    void divideWithZero() {

        //given
        MyCalculator calculator = new MyCalculator(10.0);

        //when & then
        assertThrows(MyCalculator.ZeroDivisionException.class, () ->
                calculator.divide(0.0)
        );
    }
}