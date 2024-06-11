/* (C)2024 */
package com.questionbank.questionbank.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.questionbank.questionbank.dto.RegisterTestDto;
import com.questionbank.questionbank.dto.StudentTestWriteDto;
import com.questionbank.questionbank.entity.Question;
import com.questionbank.questionbank.entity.Student;
import com.questionbank.questionbank.entity.StudentTest;
import com.questionbank.questionbank.repository.StudentRepository;
import com.questionbank.questionbank.repository.StudentTestRepository;
import com.questionbank.questionbank.repository.TestRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentTestServiceTest {

    @Mock
    private StudentTestRepository studentTestRepository;

    @Mock
    private TestRepository testRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private StudentTestService studentTestService;

    @Test
    void testGetAllStudentTest() {
        when(studentTestRepository.findAll()).thenReturn(List.of(new StudentTest()));
        final var result = studentTestService.getAllStudentTest();
        assertThat(result).isNotEmpty();
    }

    @Test
    void testFindStudentTestById() {
        when(studentTestRepository.findById(anyLong())).thenReturn(Optional.of(new StudentTest()));
        final var result = studentTestService.findStudentTestById(1L);
        assertThat(result).isNotEmpty();
    }

    @Test
    void testCreateStudentTest() {
        when(testRepository.findById(anyLong()))
                .thenReturn(Optional.of(new com.questionbank.questionbank.entity.Test()));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(new Student()));
        when(studentTestRepository.save(any(StudentTest.class))).thenReturn(new StudentTest());
        final var studentWriteDto = new StudentTestWriteDto();
        studentWriteDto.setStudentId(1L);
        studentWriteDto.setTestId(1L);
        final var result = studentTestService.createStudentTest(studentWriteDto);
        assertThat(result).isNotNull();
    }

    @Test
    void testUpdateStudentTest() {
        when(studentTestRepository.findById(anyLong())).thenReturn(Optional.of(new StudentTest()));
        when(studentTestRepository.save(any(StudentTest.class))).thenReturn(new StudentTest());
        final var result = studentTestService.updateStudentTest(Collections.emptyMap(), 1L);
        assertThat(result).isNotNull();
    }

    @Test
    void testDeleteStudentTest() {
        when(studentTestRepository.findById(anyLong())).thenReturn(Optional.of(new StudentTest()));
        final var result = studentTestService.deleteStudentTest(1L);
        assertThat(result).isNotNull();
    }

    @Test
    void testGetStudentTestsByStudentId() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(new Student()));
        when(studentTestRepository.findByStudent(any(Student.class))).thenReturn(List.of(new StudentTest()));
        final var result = studentTestService.getStudentTestsByStudentId(1L);
        assertThat(result).isNotEmpty();
    }

    @Test
    void testRegisterTest() {
        when(testRepository.findById(anyLong()))
                .thenReturn(Optional.of(new com.questionbank.questionbank.entity.Test()));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(new Student()));
        when(studentTestRepository.save(any(StudentTest.class))).thenReturn(new StudentTest());
        final var registerDto = new RegisterTestDto();
        registerDto.setTestId(1L);
        registerDto.setStudentId(1L);
        final var result = studentTestService.registerTest(registerDto);
        assertThat(result).isNotNull();
    }

    @Test
    void testCalculateScore() {
        final var test = new com.questionbank.questionbank.entity.Test();
        test.setId(1L);
        when(testRepository.findById(anyLong())).thenReturn(Optional.of(test));
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(new Student()));
        final var studentTest = new StudentTest();
        test.setId(1L);
        studentTest.setTest(test);
        when(studentTestRepository.findByStudent(any(Student.class))).thenReturn(List.of(studentTest));
        when(studentTestRepository.findStudentTestByStudentAndTest(
                        any(Student.class), any(com.questionbank.questionbank.entity.Test.class)))
                .thenReturn(new StudentTest());
        final var question = new Question();
        question.setId(1L);
        question.setCorrectAnswer("1");
        when(questionService.getQuestionsByTest(anyLong())).thenReturn(List.of(question));
        final var result = studentTestService.calculateScore(1L, 1L, Map.of(1L, "1"));
        assertThat(result).isNull();
    }
}
