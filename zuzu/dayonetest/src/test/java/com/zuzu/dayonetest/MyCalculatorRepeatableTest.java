package com.zuzu.dayonetest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyCalculatorRepeatableTest {

    // 인자값의 숫자만큼 테스트를 반복
    @RepeatedTest(5)
    @DisplayName("덧셈을 5번 단순 반복하여 테스트")
    public void repeatedAddTest() {
        // Arrange : 준비
        MyCalculator calculator = new MyCalculator();

        // Act : 행동
        calculator.add(10.0);

        // Assert : 단언/검증
        assertEquals(10.0, calculator.getResult());
    }

    // 파라미터를 받아 테스트
    @ParameterizedTest
    @MethodSource("parameterizedTestParameters")
    @DisplayName("덧셈을 5번 파라미터를 받아 반복하여 테스트")
    public void parameterizedTest(Double addValue, Double expectedValue) {
        // Arrange : 준비
        MyCalculator calculator = new MyCalculator();

        // Act : 행동
        calculator.add(addValue);

        // Assert : 단언/검증
        assertEquals(expectedValue, calculator.getResult());
    }

    //파라미터 설정
    public static Stream<Arguments> parameterizedTestParameters() {
        return Stream.of(
                Arguments.arguments(10.0, 10.0),
                Arguments.arguments(8.0, 8.0),
                Arguments.arguments(4.0, 4.0),
                Arguments.arguments(16.0, 16.0),
                Arguments.arguments(20.0, 20.0)
        );
    }

    @DisplayName("파라미터를 넣은 사칙연산 2번 반복 테스트")
    @ParameterizedTest
    @MethodSource("parameterizedComplicatedCalculateTestParameters")
    public void parameterizedComplicatedCalculateTest(
            Double addValue,
            Double minusValue,
            Double multiplyValue,
            Double divideValue,
            Double expectedValue
    ) {
        //given
        MyCalculator calculator = new MyCalculator(0.0);

        //when
        Double result = calculator
                .add(addValue)
                .minus(minusValue)
                .multiply(multiplyValue)
                .divide(divideValue)
                .getResult();

        //then
        assertEquals(expectedValue, result);
    }

    public static Stream<Arguments> parameterizedComplicatedCalculateTestParameters() {
        return Stream.of(
                Arguments.of(10.0, 4.0, 2.0, 3.0, 4.0),
                Arguments.of(4.0, 2.0, 4.0, 4.0, 2.0)
        );
    }
}
