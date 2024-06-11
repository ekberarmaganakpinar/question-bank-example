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

import com.questionbank.questionbank.dto.TestQueryDto;
import com.questionbank.questionbank.repository.TestRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestServiceTest {

    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestService testService;

    @Test
    void getAllTest() {
        when(testRepository.findAll()).thenReturn(List.of(new com.questionbank.questionbank.entity.Test()));
        final var result = testService.getAllTest();
        assertThat(result).isNotEmpty();
    }

    @Test
    void findTestById() {
        when(testRepository.findById(anyLong()))
                .thenReturn(Optional.of(new com.questionbank.questionbank.entity.Test()));
        final var result = testService.findTestById(1L);
        assertThat(result).isNotEmpty();
    }

    @Test
    void createTest() {
        when(testRepository.save(any(com.questionbank.questionbank.entity.Test.class)))
                .thenReturn(new com.questionbank.questionbank.entity.Test());
        final var result = testService.createTest(new TestQueryDto());
        assertThat(result).isNotNull();
    }

    @Test
    void updateTest() {
        when(testRepository.findById(anyLong()))
                .thenReturn(Optional.of(new com.questionbank.questionbank.entity.Test()));
        when(testRepository.save(any(com.questionbank.questionbank.entity.Test.class)))
                .thenReturn(new com.questionbank.questionbank.entity.Test());
        final var result = testService.updateTest(Collections.emptyMap(), 1L);
        assertThat(result).isNotNull();
    }

    @Test
    void deleteTest() {
        when(testRepository.findById(anyLong()))
                .thenReturn(Optional.of(new com.questionbank.questionbank.entity.Test()));
        final var result = testService.deleteTest(1L);
        assertThat(result).isNotNull();
    }
}
