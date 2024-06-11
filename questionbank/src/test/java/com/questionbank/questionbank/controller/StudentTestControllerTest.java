/* (C)2024 */
package com.questionbank.questionbank.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.questionbank.questionbank.dto.RegisterTestDto;
import com.questionbank.questionbank.dto.StudentTestQueryDto;
import com.questionbank.questionbank.dto.StudentTestWriteDto;
import com.questionbank.questionbank.entity.StudentTest;
import com.questionbank.questionbank.service.StudentTestService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentTestControllerTest {

    @Mock
    private StudentTestService studentTestService;

    @InjectMocks
    private StudentTestController studentTestController;

    @Test
    void getAllStudentTest() {
        when(studentTestService.getAllStudentTest()).thenReturn(List.of(new StudentTest()));
        final var responseEntity = studentTestController.getAllStudentTest();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void findStudentTestById() {
        when(studentTestService.findStudentTestById(anyLong())).thenReturn(Optional.of(new StudentTest()));
        final var responseEntity = studentTestController.findStudentTestById(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void createStudentTest() {
        when(studentTestService.createStudentTest(any(StudentTestWriteDto.class)))
                .thenReturn(new StudentTest());
        final var responseEntity = studentTestController.createStudentTest(new StudentTestWriteDto());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void updateStudentTest() {
        when(studentTestService.updateStudentTest(any(Map.class), anyLong())).thenReturn(new StudentTest());
        ResponseEntity<StudentTestQueryDto> responseEntity =
                studentTestController.updateStudentTest(1L, Collections.emptyMap());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void deleteStudentTest() {
        when(studentTestService.deleteStudentTest(anyLong())).thenReturn(new StudentTest());
        final var responseEntity = studentTestController.deleteStudentTest(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void getStudentTestsByStudentId() {
        when(studentTestService.getStudentTestsByStudentId(anyLong())).thenReturn(List.of(new StudentTest()));
        final var responseEntity = studentTestController.getStudentTestsByStudentId(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void registerTest() {
        when(studentTestService.registerTest(any(RegisterTestDto.class))).thenReturn(new StudentTest());
        final var responseEntity = studentTestController.registerTest(new RegisterTestDto());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void calculateScore() {
        when(studentTestService.calculateScore(anyLong(), anyLong(), anyMap())).thenReturn(new StudentTest());
        final var responseEntity = studentTestController.calculateScore(1L, 1L, Collections.emptyMap());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody()).isNotNull();
    }
}
