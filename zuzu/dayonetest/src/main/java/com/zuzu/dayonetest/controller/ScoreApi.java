package com.zuzu.dayonetest.controller;

import com.zuzu.dayonetest.controller.request.SaveExamScoreRequest;
import com.zuzu.dayonetest.controller.response.ExamFailStudentResponse;
import com.zuzu.dayonetest.controller.response.ExamPassStudentResponse;
import com.zuzu.dayonetest.service.StudentScoreService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScoreApi {

    private final StudentScoreService studentScoreService;

    @PutMapping("/exam/{exam}/score")
    public void save(
            @PathVariable("exam") String exam,
            @RequestBody SaveExamScoreRequest request
    ) {

        studentScoreService.saveScore(
                request.getStudentName(),
                exam,
                request.getKorScore(),
                request.getEnglishScore(),
                request.getMathScore()
        );
    }

    @GetMapping("exam/{exam}/pass")
    public List<ExamPassStudentResponse> pass(
            @PathVariable("exam") String exam
    ){
        return studentScoreService.getPassStudentList(exam);
    }

    @GetMapping("exam/{exam}/fail")
    public List<ExamFailStudentResponse> fail(
            @PathVariable("exam") String exam
    ) {
        return studentScoreService.getFailStudentList(exam);
    }
}
