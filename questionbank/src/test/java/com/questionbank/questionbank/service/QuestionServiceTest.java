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

import com.questionbank.questionbank.dto.QuestionWriteDto;
import com.questionbank.questionbank.entity.Question;
import com.questionbank.questionbank.repository.QuestionRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private TestService testService;

    @InjectMocks
    private QuestionService questionService;

    @Test
    void getAllQuestions() {
        when(questionRepository.findAll()).thenReturn(List.of(new Question()));
        final var result = questionService.getAllQuestion();
        assertThat(result).isNotEmpty();
    }

    @Test
    void findQuestionById() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(new Question()));
        final var result = questionService.findQuestionById(1L);
        assertThat(result).isNotEmpty();
    }

    @Test
    void createQuestion() {
        when(testService.findTestById(anyLong()))
                .thenReturn(Optional.of(new com.questionbank.questionbank.entity.Test()));
        when(questionRepository.save(any(Question.class))).thenReturn(new Question());
        final var questionWriteDto = new QuestionWriteDto();
        questionWriteDto.setTestId(1L);
        final var result = questionService.createQuestion(questionWriteDto);
        assertThat(result).isNotNull();
    }

    @Test
    void updateQuestion() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(new Question()));
        when(questionRepository.save(any(Question.class))).thenReturn(new Question());
        final var result = questionService.updateQuestion(Collections.emptyMap(), 1L);
        assertThat(result).isNotNull();
    }

    @Test
    void testCreateQuestionTestNotFound() {
        when(testService.findTestById(anyLong())).thenReturn(Optional.empty());
        final var questionWriteDto = new QuestionWriteDto();
        questionWriteDto.setTestId(1L);
        final var result = questionService.createQuestion(questionWriteDto);
        assertThat(result).isNull();
    }

    @Test
    void testUpdateQuestionWithTest() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(new Question()));
        when(testService.findTestById(anyLong()))
                .thenReturn(Optional.of(new com.questionbank.questionbank.entity.Test()));
        when(questionRepository.save(any(Question.class))).thenReturn(new Question());
        final var result = questionService.updateQuestion(Map.of("test", 1L), 1L);
        assertThat(result).isNotNull();
    }

    @Test
    void deleteQuestion() {
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(new Question()));
        final var result = questionService.deleteQuestion(1L);
        assertThat(result).isNotNull();
    }
}
