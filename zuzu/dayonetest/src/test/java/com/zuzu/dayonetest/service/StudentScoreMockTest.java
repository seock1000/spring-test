package com.zuzu.dayonetest.service;

import com.zuzu.dayonetest.controller.response.ExamFailStudentResponse;
import com.zuzu.dayonetest.controller.response.ExamPassStudentResponse;
import com.zuzu.dayonetest.model.*;
import com.zuzu.dayonetest.repository.StudentFailRepository;
import com.zuzu.dayonetest.repository.StudentPassRepository;
import com.zuzu.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class StudentScoreMockTest {

    private StudentScoreService studentScoreService;
    private StudentScoreRepository studentScoreRepository;
    private StudentPassRepository studentPassRepository;
    private StudentFailRepository studentFailRepository;

    @BeforeEach
    public void beforeEach() {
        // Mock 객체를 주입
        studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        studentPassRepository = Mockito.mock(StudentPassRepository.class);
        studentFailRepository = Mockito.mock(StudentFailRepository.class);

        studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository
        );
    }

    @Test
    @DisplayName("첫 번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        //given
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
        String givenStudentName = "석천";
        String givenExam = "testExam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        StudentScore expectedStudentScore = StudentScoreFixture.passed();

//        오버라이딩 가능
//          오버라이딩의 문제점 -> pass가 아닌 상태를 작성할 수 있음
//        StudentScore expectedStudentScore2 = StudentScoreTestDataBuilder.passed()
//                .studentName("newName")
//                .build();

        StudentPass expectedStudentPass = StudentPassFixture.create(expectedStudentScore);

        // 인자로 넘어가는 값을 capture 하는 mockito 제공 클래스
        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        //when
        studentScoreService.saveScore(
                expectedStudentScore.getStudentName(),
                expectedStudentScore.getExam(),
                expectedStudentScore.getKorScore(),
                expectedStudentScore.getEnglishScore(),
                expectedStudentScore.getMathScore()
        );

        //then
        // studentScoreRepository, studentPassRepository save 1번 호출
        // studentFailRepository save 0번 호출
        // 인자값이 제대로 넘어가 저장되는지 ArgumentCaptor 클래스로 인자값을 캡처하여 테스트
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();

        Assertions.assertEquals(expectedStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectedStudentScore.getExam(), capturedStudentScore.getExam());
        Assertions.assertEquals(expectedStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectedStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
        Assertions.assertEquals(expectedStudentScore.getMathScore(), capturedStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());
        StudentPass capturedStudentPass = studentPassArgumentCaptor.getValue();

        Assertions.assertEquals(expectedStudentPass.getStudentName(), capturedStudentPass.getStudentName());
        Assertions.assertEquals(expectedStudentPass.getExam(), capturedStudentPass.getExam());
        Assertions.assertEquals(expectedStudentPass.getAvgScore(), capturedStudentPass.getAvgScore());

        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우 Fail 저장")
    public void saveScoreMockTest2() {
        //given
        StudentScore expectedStudentScore = StudentScoreFixture.failed();

        StudentFail expectedStudentFail = StudentFailFixture.create(expectedStudentScore);

        // 인자로 넘어가는 값을 capture 하는 mockito 제공 클래스
        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

        //when
        studentScoreService.saveScore(
                expectedStudentScore.getStudentName(),
                expectedStudentScore.getExam(),
                expectedStudentScore.getKorScore(),
                expectedStudentScore.getEnglishScore(),
                expectedStudentScore.getMathScore()
        );

        //then
        // studentScoreRepository, studentFailRepository save 1번 호출
        // studentPassRepository save 0번 호출
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();

        Assertions.assertEquals(expectedStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectedStudentScore.getExam(), capturedStudentScore.getExam());
        Assertions.assertEquals(expectedStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectedStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
        Assertions.assertEquals(expectedStudentScore.getMathScore(), capturedStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());
        StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();

        Assertions.assertEquals(expectedStudentFail.getStudentName(), capturedStudentFail.getStudentName());
        Assertions.assertEquals(expectedStudentFail.getExam(), capturedStudentFail.getExam());
        Assertions.assertEquals(expectedStudentFail.getAvgScore(), capturedStudentFail.getAvgScore());
    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    public void getPassStudentsListTest() {
        // given
        String givenExam = "testExam";
        StudentPass expectedStudent1 = StudentPassFixture.create("석천1", givenExam);
        StudentPass expectedStudent2 = StudentPassFixture.create("석천2", givenExam);
        StudentPass notExpectedStudent = StudentPassFixture.create("not", "notgiven");

        // findAll 호출 시 지정한 List 호출
        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectedStudent1,
                expectedStudent2,
                notExpectedStudent
        ));

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
        String givenExam = "testExam";

        StudentFail expectedStudent1 = StudentFailFixture.create("석천1", givenExam);
        StudentFail expectedStudent2 = StudentFailFixture.create("석천2", givenExam);
        StudentFail notExpectedStudent = StudentFailFixture.create("not", "notgiven");

        // findAll 호출 시 지정한 List 호출
        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
                expectedStudent1,
                expectedStudent2,
                notExpectedStudent
        ));

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
