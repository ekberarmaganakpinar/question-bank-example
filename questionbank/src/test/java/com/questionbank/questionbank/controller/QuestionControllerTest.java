/* (C)2024 */
package com.questionbank.questionbank.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.questionbank.questionbank.dto.QuestionWriteDto;
import com.questionbank.questionbank.entity.Question;
import com.questionbank.questionbank.service.QuestionService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private QuestionController questionController;

    @Test
    void getAllQuestion() {
        final var question = new Question();
        when(questionService.getAllQuestion()).thenReturn(List.of(question));
        final var responseEntity = questionController.getAllQuestion();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseEntity.getBody().get(0).getQuestionText()).isEqualTo(question.getQuestionText());
    }

    @Test
    void findQuestionById() {
        final var question = new Question();
        when(questionService.findQuestionById(anyLong())).thenReturn(Optional.of(question));
        final var responseEntity = questionController.findQuestionById(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(responseEntity.getBody().getQuestionText()).isEqualTo(question.getQuestionText());
    }

    @Test
    void createQuestion() {
        final var question = new Question();
        final var questionWriteDto = new QuestionWriteDto();
        when(questionService.createQuestion(any(QuestionWriteDto.class))).thenReturn(question);
        final var responseEntity = questionController.createQuestion(questionWriteDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getQuestionText()).isEqualTo(question.getQuestionText());
    }

    @Test
    void updateQuestion() {
        final var question = new Question();
        final var fields = Map.of("questionText", (Object) "Updated Question");
        when(questionService.updateQuestion(any(Map.class), anyLong())).thenReturn(question);
        final var responseEntity = questionController.updateQuestion(1L, fields);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody().getQuestionText()).isEqualTo(question.getQuestionText());
    }

    @Test
    void deleteQuestion() {
        final var question = new Question();
        when(questionService.deleteQuestion(anyLong())).thenReturn(question);
        final var responseEntity = questionController.deleteQuestion(1L);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(responseEntity.getBody().getQuestionText()).isEqualTo(question.getQuestionText());
    }
}
