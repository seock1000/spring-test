package com.zuzu.dayonetest.model;

import com.zuzu.dayonetest.MyCalculator;

public class StudentFailFixture {

    public static StudentFail create(StudentScore studentScore) {

        var calculator = new MyCalculator(0.0);

        return StudentFail
                .builder()
                .exam(studentScore.getExam())
                .studentName(studentScore.getStudentName())
                .avgScore(calculator
                        .add(studentScore.getKorScore().doubleValue())
                        .add(studentScore.getEnglishScore().doubleValue())
                        .add(studentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult())
                .build();
    }

    public static StudentFail create(String studentName, String exam) {
        return StudentFail.builder()
                .studentName(studentName)
                .exam(exam)
                .avgScore(40.0)
                .build();
    }

}
