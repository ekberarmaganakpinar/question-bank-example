/* (C)2024 */
package com.questionbank.questionbank.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.questionbank.questionbank.dto.TestQueryDto;
import com.questionbank.questionbank.mapper.DtoMapper;
import com.questionbank.questionbank.service.TestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("/get")
    public ResponseEntity<List<TestQueryDto>> getAllTests() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(testService.getAllTest().stream()
                        .map(DtoMapper::testEntityToDtoMapper)
                        .toList());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<TestQueryDto> findTestById(@PathVariable final Long id) {
        return testService
                .findTestById(id)
                .map(value -> ResponseEntity.status(HttpStatus.FOUND).body(DtoMapper.testEntityToDtoMapper(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PostMapping("/create")
    public ResponseEntity<TestQueryDto> createTest(@RequestBody final TestQueryDto testDto) {
        final var test = Optional.ofNullable(testService.createTest(testDto));
        return test.map(value -> ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.testEntityToDtoMapper(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<TestQueryDto> updateTest(
            @PathVariable final Long id, @RequestBody final Map<String, Object> fields) {
        final var test = Optional.ofNullable(testService.updateTest(fields, id));
        return test.map(value ->
                        ResponseEntity.status(HttpStatus.ACCEPTED).body(DtoMapper.testEntityToDtoMapper(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TestQueryDto> deleteTest(@PathVariable final Long id) {
        final var test = Optional.ofNullable(testService.deleteTest(id));
        return test.map(value ->
                        ResponseEntity.status(HttpStatus.ACCEPTED).body(DtoMapper.testEntityToDtoMapper(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }
}
