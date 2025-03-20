package com.zuzu.dayonetest.service;

import com.zuzu.dayonetest.IntegrationTest;
import com.zuzu.dayonetest.MyCalculator;
import com.zuzu.dayonetest.model.StudentScoreFixture;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentScoreServiceIntegrationTest extends IntegrationTest {
    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private EntityManager em;

    @Test
    public void savePassedStudentScoreTest() {
        //given
        var studentScore = StudentScoreFixture.passed();

        //when
        studentScoreService.saveScore(
                studentScore.getStudentName(),
                studentScore.getExam(),
                studentScore.getKorScore(),
                studentScore.getEnglishScore(),
                studentScore.getMathScore()
        );

        em.flush();
        em.clear();

        //then
        var passedStudentResponses = studentScoreService.getPassStudentList(studentScore.getExam());

        Assertions.assertEquals(passedStudentResponses.size(), 1);

        var passedStudentResponse = passedStudentResponses.get(0);
        var calculator = new MyCalculator(0.0);

        Assertions.assertEquals(passedStudentResponse.getStudentName(), studentScore.getStudentName());
        Assertions.assertEquals(passedStudentResponse.getAvgScore(),
                calculator
                        .add(studentScore.getKorScore().doubleValue())
                        .add(studentScore.getEnglishScore().doubleValue())
                        .add(studentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult()
        );
    }

    @Test
    public void saveFailedStudentScoreTest() {
        //given
        var studentScore = StudentScoreFixture.failed();

        //when
        studentScoreService.saveScore(
                studentScore.getStudentName(),
                studentScore.getExam(),
                studentScore.getKorScore(),
                studentScore.getEnglishScore(),
                studentScore.getMathScore()
        );
        em.flush();
        em.clear();

        //when
        var failedStudentResponses = studentScoreService.getFailStudentList(studentScore.getExam());
        Assertions.assertEquals(failedStudentResponses.size(), 1);

        var failedStudentResponse = failedStudentResponses.get(0);
        var calculator = new MyCalculator(0.0);

        Assertions.assertEquals(failedStudentResponse.getStudentName(), studentScore.getStudentName());
        Assertions.assertEquals(failedStudentResponse.getAvgScore(),
                calculator
                        .add(studentScore.getKorScore().doubleValue())
                        .add(studentScore.getEnglishScore().doubleValue())
                        .add(studentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult()
        );


    }
}
