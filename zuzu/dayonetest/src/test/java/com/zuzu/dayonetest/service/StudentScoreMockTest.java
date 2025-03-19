package com.zuzu.dayonetest.service;

import com.zuzu.dayonetest.controller.response.ExamFailStudentResponse;
import com.zuzu.dayonetest.controller.response.ExamPassStudentResponse;
import com.zuzu.dayonetest.model.StudentFail;
import com.zuzu.dayonetest.model.StudentPass;
import com.zuzu.dayonetest.repository.StudentFailRepository;
import com.zuzu.dayonetest.repository.StudentPassRepository;
import com.zuzu.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class StudentScoreMockTest {

    @Test
    @DisplayName("첫 번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        //given
        // Mock 객체를 주입
        StudentScoreService studentScoreService = new StudentScoreService(
                Mockito.mock(StudentScoreRepository.class),
                Mockito.mock(StudentPassRepository.class),
                Mockito.mock(StudentFailRepository.class)
        );
        String givenStudentName = "석천";
        String givenExam = "testExam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        //when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우 Pass 저장")
    public void saveScoreMockTest() {
        //given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        String givenStudentName = "석천";
        String givenExam = "testExam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        //when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );

        //then
        // studentScoreRepository, studentPassRepository save 1번 호출
        // studentFailRepository save 0번 호출
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우 Fail 저장")
    public void saveScoreMockTest2() {
        //given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        String givenStudentName = "석천";
        String givenExam = "testExam";
        Integer givenKorScore = 40;
        Integer givenEnglishScore = 20;
        Integer givenMathScore = 60;

        //when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );

        //then
        // studentScoreRepository, studentFailRepository save 1번 호출
        // studentPassRepository save 0번 호출
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    public void getPassStudentsListTest() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentPass expectedStudent1 = StudentPass.builder().id(1L).studentName("석천").exam("testExam").avgScore(70.0).build();
        StudentPass expectedStudent2 = StudentPass.builder().id(2L).studentName("석천2").exam("testExam").avgScore(70.0).build();
        StudentPass notExpectedStudent = StudentPass.builder().id(3L).studentName("not").exam("notTestExam").avgScore(70.0).build();

        // findAll 호출 시 지정한 List 호출
        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectedStudent1,
                expectedStudent2,
                notExpectedStudent
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );
        String givenExam = "testExam";

        //when
        var expectedResponses = List.of(expectedStudent1, expectedStudent2)
                .stream()
                .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
        List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentList(givenExam);

        Assertions.assertIterableEquals(expectedResponses, responses);
    }

    @Test
    @DisplayName("불합격자 명단 가져오기 검증")
    public void getFailStudentsListTest() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);
        String givenExam = "testExam";

        StudentFail expectedStudent1 = StudentFail.builder().id(1L).studentName("석천").exam(givenExam).avgScore(50.0).build();
        StudentFail expectedStudent2 = StudentFail.builder().id(2L).studentName("석천2").exam(givenExam).avgScore(40.0).build();
        StudentFail notExpectedStudent = StudentFail.builder().id(3L).studentName("not").exam("not").avgScore(30.0).build();

        // findAll 호출 시 지정한 List 호출
        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
                expectedStudent1,
                expectedStudent2,
                notExpectedStudent
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );

        //when
        var expectedFailList = List.of(expectedStudent1, expectedStudent2)
                .stream()
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
                .toList();
        List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentList(givenExam);

        //then
        Assertions.assertIterableEquals(expectedFailList, responses);
    }
}
