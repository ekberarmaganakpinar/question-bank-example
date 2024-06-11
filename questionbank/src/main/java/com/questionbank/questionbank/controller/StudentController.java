/* (C)2024 */
package com.questionbank.questionbank.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.questionbank.questionbank.dto.StudentQueryDto;
import com.questionbank.questionbank.dto.StudentReadDto;
import com.questionbank.questionbank.mapper.DtoMapper;
import com.questionbank.questionbank.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/get")
    public ResponseEntity<List<StudentReadDto>> getAllStudent() {

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(studentService.getAllStudent().stream()
                        .map(DtoMapper::studentEntityToDtoMapperAllData)
                        .toList());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<StudentQueryDto> findStudentById(@PathVariable final Long id) {
        return studentService
                .findStudentById(id)
                .map(value -> ResponseEntity.status(HttpStatus.FOUND).body(DtoMapper.studentEntityToDtoMapper(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/create")
    public ResponseEntity<StudentQueryDto> createStudent(@RequestBody final StudentQueryDto studentQueryDto) {
        final var student = Optional.ofNullable(studentService.createStudent(studentQueryDto));
        return student.map(value ->
                        ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.studentEntityToDtoMapper(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<StudentQueryDto> updateStudent(
            @PathVariable final Long id, @RequestBody final Map<String, Object> fields) {
        final var student = Optional.ofNullable(studentService.updateStudent(fields, id));
        return student.map(value ->
                        ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.studentEntityToDtoMapper(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StudentQueryDto> deleteStudent(@PathVariable final Long id) {
        final var student = Optional.ofNullable(studentService.deleteStudent(id));
        return student.map(value ->
                        ResponseEntity.status(HttpStatus.CREATED).body(DtoMapper.studentEntityToDtoMapper(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
    }
}
