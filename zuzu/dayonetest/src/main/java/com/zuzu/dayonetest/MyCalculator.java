package com.zuzu.dayonetest;

public class MyCalculator {

    private Double result;

    public MyCalculator(Double initResult) {
        this.result = initResult;
    }

    public MyCalculator() {
        this.result = 0.0;
    }


    public MyCalculator add(Double value) {
        this.result += value;
        return this;
    }

    public MyCalculator minus(Double value) {
        this.result -= value;
        return this;
    }

    public MyCalculator divide(Double value) {
        if(value == 0.0) {
            throw new ZeroDivisionException();
        }
        this.result /= value;
        return this;
    }

    public MyCalculator multiply(Double value) {
        this.result *= value;
        return this;
    }

    public Double getResult() {
        return this.result;
    }

    public static class ZeroDivisionException extends RuntimeException {

    }

}
