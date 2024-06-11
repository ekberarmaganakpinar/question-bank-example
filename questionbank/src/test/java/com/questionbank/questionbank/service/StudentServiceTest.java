/* (C)2024 */
package com.questionbank.questionbank.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.questionbank.questionbank.dto.StudentQueryDto;
import com.questionbank.questionbank.entity.Student;
import com.questionbank.questionbank.repository.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void getAllStudent_shouldReturnListOfStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(new Student()));
        final var result = studentService.getAllStudent();
        assertThat(result).isNotEmpty();
    }

    @Test
    void findStudentById_shouldReturnStudent() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(new Student()));
        final var result = studentService.findStudentById(1L);
        assertThat(result).isNotEmpty();
    }

    @Test
    void createStudent_shouldCreateAndReturnStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(new Student());
        final var result = studentService.createStudent(new StudentQueryDto());
        assertThat(result).isNotNull();
    }

    @Test
    void updateStudent_shouldUpdateAndReturnStudent() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(new Student()));
        when(studentRepository.save(any(Student.class))).thenReturn(new Student());
        final var result = studentService.updateStudent(Collections.emptyMap(), 1L);
        assertThat(result).isNotNull();
    }

    @Test
    void deleteStudent_shouldDeleteAndReturnStudent() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(new Student()));
        final var result = studentService.deleteStudent(1L);
        assertThat(result).isNotNull();
    }
}
