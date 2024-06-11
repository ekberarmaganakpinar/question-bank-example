/* (C)2024 */
package com.questionbank.questionbank.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.questionbank.questionbank.dto.*;
import com.questionbank.questionbank.mapper.DtoMapper;
import com.questionbank.questionbank.service.StudentTestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student-test")
@RequiredArgsConstructor
public class StudentTestController {
    private final StudentTestService studentTestService;

    @GetMapping("/get")
    public ResponseEntity<List<StudentTestReadDto>> getAllStudentTest() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(studentTestService.getAllStudentTest().stream()
                        .map(DtoMapper::studentTestEntityToDtoMapperAllData)
                        .toList());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<StudentTestQueryDto> findStudentTestById(@PathVariable final Long id) {
        return studentTestService
                .findStudentTestById(id)
                .map(value ->
                        ResponseEntity.status(HttpStatus.FOUND).body(DtoMapper.studentTestEntityToDtoMapper(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/create")
    public ResponseEntity<StudentTestQueryDto> createStudentTest(
            @RequestBody final StudentTestWriteDto studentTestWriteDto) {
        final var studentTestOpt = Optional.ofNullable(studentTestService.createStudentTest(studentTestWriteDto));
        return studentTestOpt
                .map(value ->
                        ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.studentTestEntityToDtoMapper(value)))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<StudentTestQueryDto> updateStudentTest(
            @PathVariable final Long id, @RequestBody final Map<String, Object> fields) {
        final var studentTestOpt = Optional.ofNullable(studentTestService.updateStudentTest(fields, id));
        return studentTestOpt
                .map(value ->
                        ResponseEntity.status(HttpStatus.ACCEPTED).body(DtoMapper.studentTestEntityToDtoMapper(value)))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StudentTestQueryDto> deleteStudentTest(@PathVariable final Long id) {
        final var studentTestOpt = Optional.ofNullable(studentTestService.deleteStudentTest(id));
        return studentTestOpt
                .map(value ->
                        ResponseEntity.status(HttpStatus.ACCEPTED).body(DtoMapper.studentTestEntityToDtoMapper(value)))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @GetMapping("/student-by-test/{id}")
    public ResponseEntity<List<StudentTestQueryDto>> getStudentTestsByStudentId(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(studentTestService.getStudentTestsByStudentId(id).stream()
                        .map(DtoMapper::studentTestEntityToDtoMapper)
                        .toList());
    }

    @PostMapping("/register")
    public ResponseEntity<StudentTestQueryDto> registerTest(@RequestBody final RegisterTestDto registerTestDto) {
        final var studentTestOpt = Optional.ofNullable(studentTestService.registerTest(registerTestDto));
        return studentTestOpt
                .map(value ->
                        ResponseEntity.status(HttpStatus.ACCEPTED).body(DtoMapper.studentTestEntityToDtoMapper(value)))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @GetMapping("/calculate-score/{testId}/{studentId}")
    public ResponseEntity<StudentTestQueryDto> calculateScore(
            @PathVariable final Long testId,
            @PathVariable final Long studentId,
            @RequestBody final Map<Long, String> questionsAndAnswer) {
        final var studentTestOpt =
                Optional.ofNullable(studentTestService.calculateScore(testId, studentId, questionsAndAnswer));
        return studentTestOpt
                .map(value ->
                        ResponseEntity.status(HttpStatus.ACCEPTED).body(DtoMapper.studentTestEntityToDtoMapper(value)))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }
}
