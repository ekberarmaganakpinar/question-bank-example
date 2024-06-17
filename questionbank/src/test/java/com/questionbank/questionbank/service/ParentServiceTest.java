/* (C)2024 */
package com.questionbank.questionbank.service;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.questionbank.questionbank.entity.Student;
import com.questionbank.questionbank.repository.ParentRepository;
import com.questionbank.questionbank.repository.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParentServiceTest {

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private ParentService parentService;

    @Test
    void findParentsById() {
        when(parentRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(new Student()));
        final var result = parentService.findParentByStudentId(1L);
        assertThat(result).isEmpty();
    }
}
