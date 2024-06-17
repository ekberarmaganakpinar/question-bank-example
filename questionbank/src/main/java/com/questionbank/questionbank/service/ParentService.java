/* (C)2024 */
package com.questionbank.questionbank.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.questionbank.questionbank.entity.Parent;
import com.questionbank.questionbank.repository.ParentRepository;
import com.questionbank.questionbank.repository.StudentRepository;
import com.questionbank.questionbank.specification.ParentSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;

    public List<Parent> findParentByStudentId(final Long studentId) {
        final var student = studentRepository.findById(studentId);
        return student.map(value -> parentRepository.findAll(ParentSpecification.hasStudent(value)))
                .orElse(Collections.emptyList());
    }
}
