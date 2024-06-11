/* (C)2024 */
package com.questionbank.questionbank.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.questionbank.questionbank.dto.QuestionQueryDto;
import com.questionbank.questionbank.dto.QuestionReadDto;
import com.questionbank.questionbank.dto.QuestionWriteDto;
import com.questionbank.questionbank.mapper.DtoMapper;
import com.questionbank.questionbank.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/get")
    public ResponseEntity<List<QuestionReadDto>> getAllQuestion() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(questionService.getAllQuestion().stream()
                        .map(DtoMapper::questionEntityToDtoMapperAllData)
                        .toList());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<QuestionQueryDto> findQuestionById(@PathVariable final Long id) {
        return questionService
                .findQuestionById(id)
                .map(value -> ResponseEntity.status(HttpStatus.FOUND).body(DtoMapper.questionEntityToDtoMapper(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PostMapping("/create")
    public ResponseEntity<QuestionQueryDto> createQuestion(@RequestBody final QuestionWriteDto questionDto) {
        final var question = Optional.ofNullable(questionService.createQuestion(questionDto));

        return question.map(value -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(QuestionQueryDto.builder()
                                .questionText(value.getQuestionText())
                                .correctAnswer(value.getCorrectAnswer())
                                .options(value.getOptions())
                                .test(value.getTest())
                                .build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<QuestionQueryDto> updateQuestion(
            @PathVariable final Long id, @RequestBody final Map<String, Object> fields) {
        final var question = Optional.ofNullable(questionService.updateQuestion(fields, id));

        return question.map(value -> ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(QuestionQueryDto.builder()
                                .questionText(value.getQuestionText())
                                .correctAnswer(value.getCorrectAnswer())
                                .options(value.getOptions())
                                .test(value.getTest())
                                .build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<QuestionQueryDto> deleteQuestion(@PathVariable final Long id) {
        final var question = Optional.ofNullable(questionService.deleteQuestion(id));

        return question.map(value -> ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(QuestionQueryDto.builder()
                                .questionText(value.getQuestionText())
                                .correctAnswer(value.getCorrectAnswer())
                                .options(value.getOptions())
                                .test(value.getTest())
                                .build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }
}
