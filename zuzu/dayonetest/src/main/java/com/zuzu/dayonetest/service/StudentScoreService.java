package com.zuzu.dayonetest.service;

import com.zuzu.dayonetest.MyCalculator;
import com.zuzu.dayonetest.controller.response.ExamFailStudentResponse;
import com.zuzu.dayonetest.controller.response.ExamPassStudentResponse;
import com.zuzu.dayonetest.model.StudentFail;
import com.zuzu.dayonetest.model.StudentPass;
import com.zuzu.dayonetest.model.StudentScore;
import com.zuzu.dayonetest.repository.StudentFailRepository;
import com.zuzu.dayonetest.repository.StudentPassRepository;
import com.zuzu.dayonetest.repository.StudentScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentScoreService {

    private final StudentScoreRepository studentScoreRepository;
    private final StudentPassRepository studentPassRepository;
    private final StudentFailRepository studentFailRepository;

    public void saveScore(String studentName, String exam, Integer korScore, Integer englishScore, Integer mathScore) {
        StudentScore studentScore = StudentScore.builder()
                .studentName(studentName)
                .exam(exam)
                .korScore(korScore)
                .englishScore(englishScore)
                .mathScore(mathScore)
                .build();

        studentScoreRepository.save(studentScore);

        Double avgScore = new MyCalculator(0.0)
                .add(korScore.doubleValue())
                .add(englishScore.doubleValue())
                .add(mathScore.doubleValue())
                .divide(3.0)
                .getResult();

        if(avgScore >= 60) {
            studentPassRepository.save(
                    StudentPass.builder()
                            .exam(exam)
                            .studentName(studentName)
                            .avgScore(avgScore)
                            .build()
            );
        } else {
            studentFailRepository.save(
                    StudentFail.builder()
                            .exam(exam)
                            .studentName(studentName)
                            .avgScore(avgScore)
                            .build()
            );
        }
    }

    public List<ExamPassStudentResponse> getPassStudentList(String exam) {
        List<StudentPass> studentPasses = studentPassRepository.findAll();

        return studentPasses.stream()
                .filter((pass) -> pass.getExam().equals(exam))
                .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
    }

    public List<ExamFailStudentResponse> getFailStudentList(String exam) {
        List<StudentFail> studentFails = studentFailRepository.findAll();

        return studentFails.stream()
                .filter((fail) -> fail.getExam().equals(exam))
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
                .toList();
    }
}
