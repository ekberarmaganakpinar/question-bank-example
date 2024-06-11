/* (C)2024 */
package com.questionbank.questionbank.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.questionbank.questionbank.dto.StudentQueryDto;
import com.questionbank.questionbank.entity.Student;
import com.questionbank.questionbank.mapper.DtoMapper;
import com.questionbank.questionbank.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    @Cacheable(value = "cache2", key = "'#allStudents'")
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Cacheable(value = "cache2", key = "#id")
    public Optional<Student> findStudentById(final Long id) {
        return studentRepository.findById(id);
    }

    @Transactional
    @CachePut(value = "cache2", key = "#result.id")
    @CacheEvict(value = "cache2", key = "'#allStudents'")
    public Student createStudent(final StudentQueryDto studentDto) {
        try {
            final var studentEntity = DtoMapper.studentDtoToEntityMapper(studentDto);
            return studentRepository.save(studentEntity);
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    @Transactional
    @CachePut(value = "cache2", key = "#result.id")
    @CacheEvict(value = "cache2", key = "'#allStudents'")
    public Student updateStudent(final Map<String, Object> fields, final Long id) {
        final var studentOpt = studentRepository.findById(id);
        if (studentOpt.isEmpty()) {
            return null;
        }
        final var student = studentOpt.get();
        try {
            fields.forEach((key, value) -> {
                final var field = ReflectionUtils.findField(Student.class, key);
                if (Objects.nonNull(field)) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, student, value);
                }
            });

            return studentRepository.save(student);
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    @Transactional
    @CacheEvict(value = "cache2", key = "'#allStudents'")
    public Student deleteStudent(final Long id) {
        if (studentRepository.findById(id).isEmpty()) {
            return null;
        }
        try {
            studentRepository.deleteById(id);
            return new Student();
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }
}
