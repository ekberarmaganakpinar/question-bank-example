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

import com.questionbank.questionbank.dto.StudentQueryDto;
import com.questionbank.questionbank.entity.Student;
import com.questionbank.questionbank.service.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void getAllStudent() {
        when(studentService.getAllStudent()).thenReturn(List.of(new Student()));
        final var responseEntity = studentController.getAllStudent();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    void findStudentById() {
        when(studentService.findStudentById(anyLong())).thenReturn(Optional.of(new Student()));
        final var responseEntity = studentController.findStudentById(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseEntity.getBody().getFirstName()).isEqualTo(null);
    }

    @Test
    void createStudent() {
        when(studentService.createStudent(any(StudentQueryDto.class))).thenReturn(new Student());
        final var responseEntity = studentController.createStudent(new StudentQueryDto());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getFirstName()).isEqualTo(null);
    }

    @Test
    void updateStudent() {
        when(studentService.updateStudent(any(Map.class), anyLong())).thenReturn(new Student());
        final var responseEntity = studentController.updateStudent(1L, Collections.emptyMap());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getFirstName()).isEqualTo(null);
    }

    @Test
    void deleteStudent() {
        when(studentService.deleteStudent(anyLong())).thenReturn(new Student());
        final var responseEntity = studentController.deleteStudent(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getFirstName()).isEqualTo(null);
    }
}
