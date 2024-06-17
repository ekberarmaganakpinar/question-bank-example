/* (C)2024 */
package com.questionbank.questionbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.questionbank.questionbank.dto.ParentDto;
import com.questionbank.questionbank.mapper.DtoMapper;
import com.questionbank.questionbank.service.ParentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @GetMapping("/find/{studentId}")
    public ResponseEntity<List<ParentDto>> findParentByStudentId(@PathVariable final Long studentId) {
        final var parents = parentService.findParentByStudentId(studentId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(parents.stream().map(DtoMapper::parentEntityByDtoMapper).toList());
    }
}
