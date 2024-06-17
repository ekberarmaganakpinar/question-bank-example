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

import com.questionbank.questionbank.dto.TestQueryDto;
import com.questionbank.questionbank.service.TestService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestControllerTest {

    @Mock
    private TestService testService;

    @InjectMocks
    private TestController testController;

    @Test
    void getAllTests() {
        when(testService.getAllTest()).thenReturn(List.of(new com.questionbank.questionbank.entity.Test()));
        final var responseEntity = testController.getAllTests();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    void findTestById() {
        when(testService.findTestById(anyLong()))
                .thenReturn(Optional.of(new com.questionbank.questionbank.entity.Test()));
        final var responseEntity = testController.findTestById(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void createTest() {
        when(testService.createTest(any(TestQueryDto.class)))
                .thenReturn(new com.questionbank.questionbank.entity.Test());

        final var responseEntity = testController.createTest(new TestQueryDto());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void updateTest() {
        when(testService.updateTest(any(Map.class), anyLong()))
                .thenReturn(new com.questionbank.questionbank.entity.Test());
        ResponseEntity<TestQueryDto> responseEntity = testController.updateTest(1L, Collections.emptyMap());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void deleteTest() {
        when(testService.deleteTest(anyLong())).thenReturn(new com.questionbank.questionbank.entity.Test());
        final var responseEntity = testController.deleteTest(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody()).isNotNull();
    }
}
