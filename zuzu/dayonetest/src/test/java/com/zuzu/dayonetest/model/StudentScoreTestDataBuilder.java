package com.zuzu.dayonetest.model;

/**
 * TestDataBuilder pattern
 */
public class StudentScoreTestDataBuilder {

    public static StudentScore.StudentScoreBuilder passed() {
        return StudentScore.builder()
                .korScore(80)
                .englishScore(100)
                .mathScore(80)
                .studentName("defaultName")
                .exam("defaultExam");
    }

    public static StudentScore.StudentScoreBuilder failed() {
        return StudentScore.builder()
                .korScore(50)
                .englishScore(40)
                .mathScore(30)
                .studentName("defaultName")
                .exam("defaultExam");
    }
}
