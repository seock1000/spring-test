package com.zuzu.dayonetest.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class ExamFailStudentResponse {
    private final String studentName;
    private final Double avgScore;
}
