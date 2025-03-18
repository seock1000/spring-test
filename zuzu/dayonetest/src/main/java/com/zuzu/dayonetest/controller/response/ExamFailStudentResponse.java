package com.zuzu.dayonetest.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExamPassStudentResponse {
    private final String studentName;
    private final Double avgScore;
}
