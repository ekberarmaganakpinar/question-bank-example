/* (C)2024 */
package com.questionbank.questionbank.service;

import java.math.BigDecimal;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.questionbank.questionbank.dto.*;
import com.questionbank.questionbank.entity.Question;
import com.questionbank.questionbank.entity.Student;
import com.questionbank.questionbank.entity.StudentTest;
import com.questionbank.questionbank.entity.Test;
import com.questionbank.questionbank.exception.QuestionBankNotFoundException;
import com.questionbank.questionbank.exception.QuestionBankTestProcessException;
import com.questionbank.questionbank.mapper.DtoMapper;
import com.questionbank.questionbank.repository.StudentRepository;
import com.questionbank.questionbank.repository.StudentTestRepository;
import com.questionbank.questionbank.repository.TestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentTestService {
    private final StudentTestRepository studentTestRepository;
    private final TestRepository testRepository;
    private final StudentRepository studentRepository;
    private final QuestionService questionService;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentTestService.class);

    @Cacheable(value = "cache4", key = "'#allStudentTests'")
    public List<StudentTest> getAllStudentTest() {
        return studentTestRepository.findAll();
    }

    @Cacheable(value = "cache4", key = "#id")
    public Optional<StudentTest> findStudentTestById(final Long id) {
        return studentTestRepository.findById(id);
    }

    @Transactional
    @CachePut(value = "cache4", key = "#result.id")
    @CacheEvict(value = "cache4", key = "'#allStudentTests'")
    public StudentTest createStudentTest(final StudentTestWriteDto studentTestWriteDto) {
        try {
            final var testOpt = testRepository.findById(studentTestWriteDto.getTestId());
            final var studentOpt = studentRepository.findById(studentTestWriteDto.getStudentId());
            checkValidations(testOpt, studentOpt);
            final var test = testOpt.get();
            final var student = studentOpt.get();
            final var studentTestEntity = DtoMapper.studentTestDtoToEntityMapper(studentTestWriteDto, student, test);
            return studentTestRepository.save(studentTestEntity);

        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    @Transactional
    @CachePut(value = "cache4", key = "#result.id")
    @CacheEvict(value = "cache4", key = "'#allStudentTests'")
    public StudentTest updateStudentTest(final Map<String, Object> fields, final Long id) {
        final var studentTestOpt = studentTestRepository.findById(id);
        if (studentTestOpt.isEmpty()) {
            return null;
        }
        final var studentTest = studentTestOpt.get();
        try {
            fields.forEach((key, value) -> {
                final var field = ReflectionUtils.findField(StudentTest.class, key);
                if (Objects.nonNull(field)) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, studentTest, getUpdateValue(key, value));
                }
            });

            return studentTestRepository.save(studentTest);
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    private Object getUpdateValue(final String key, final Object value) {
        return switch (key) {
            case "test" -> getTestValue(value);
            case "student" -> getStudentValue(value);
            default -> value;
        };
    }

    private Student getStudentValue(final Object value) {
        final var studentOpt = studentRepository.findById(Long.valueOf(value.toString()));
        if (studentOpt.isEmpty()) {
            throw new QuestionBankNotFoundException("Student is not found!!");
        }
        return studentOpt.get();
    }

    private Test getTestValue(final Object value) {
        final var testOpt = testRepository.findById(Long.valueOf(value.toString()));
        if (testOpt.isEmpty()) {
            throw new QuestionBankNotFoundException("Test is not found!!");
        }
        return testOpt.get();
    }

    @Transactional
    @CacheEvict(value = "cache4", key = "'#allStudentTests'")
    public StudentTest deleteStudentTest(final Long id) {
        if (studentTestRepository.findById(id).isEmpty()) {
            return null;
        }
        try {
            studentTestRepository.deleteById(id);
            return new StudentTest();
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    public List<StudentTest> getStudentTestsByStudentId(final Long studentId) {
        return studentRepository
                .findById(studentId)
                .map(studentTestRepository::findByStudent)
                .orElse(Collections.emptyList());
    }

    private List<StudentTest> getStudentTestByStudentId(final Long studentId) {
        return studentRepository
                .findById(studentId)
                .map(studentTestRepository::findByStudent)
                .orElse(Collections.emptyList());
    }

    @Transactional
    @CachePut(value = "cache4", key = "#result.id")
    @CacheEvict(value = "cache4", key = "'#allStudentTests'")
    public StudentTest registerTest(final RegisterTestDto registerTestDto) {
        try {
            final var test = testRepository.findById(registerTestDto.getTestId());
            final var student = studentRepository.findById(registerTestDto.getStudentId());
            checkValidationsForRegister(test, student);
            final var studentTest = createRecordStudentTest(test.get(), student.get());
            return studentTestRepository.save(studentTest);
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    private void checkValidationsForRegister(final Optional<Test> test, final Optional<Student> student) {
        checkValidations(test, student);
        final var studentTests = getStudentTestByStudentId(student.get().getId());
        if (isStudentRegistered(test.get().getId(), studentTests)) {
            throw new QuestionBankTestProcessException("Student had record");
        }
    }

    private void checkValidationsForCalculation(final Optional<Test> test, final Optional<Student> student) {
        checkValidations(test, student);
        final var studentTests =
                student.map(studentTestRepository::findByStudent).orElse(Collections.emptyList());
        if (!isStudentRegistered(test.get().getId(), studentTests)) {
            throw new QuestionBankTestProcessException("Student had not record");
        }
    }

    private static void checkValidations(final Optional<Test> test, final Optional<Student> student) {
        if (test.isEmpty()) {
            throw new QuestionBankNotFoundException("Test id not found");
        }

        if (student.isEmpty()) {
            throw new QuestionBankNotFoundException("Student is not found");
        }
    }

    private StudentTest createRecordStudentTest(final Test test, final Student student) {
        final var studentTest = new StudentTest();
        studentTest.setScore(0);
        studentTest.setStudent(student);
        studentTest.setTest(test);
        return studentTest;
    }

    private static boolean isStudentRegistered(final Long testId, final List<StudentTest> studentTests) {
        return studentTests.stream()
                .anyMatch(studentTest -> Objects.equals(studentTest.getTest().getId(), testId));
    }

    @Transactional
    @CachePut(value = "cache4", key = "#result.id")
    @CacheEvict(value = "cache4", key = "'#allStudentTests'")
    public StudentTest calculateScore(
            final Long testId, final Long studentId, final Map<Long, String> questionsAndAnswer) {
        try {
            final var test = testRepository.findById(testId);
            final var student = studentRepository.findById(studentId);
            checkValidationsForCalculation(test, student);
            final var questionsByTest = questionService.getQuestionsByTest(testId);
            final var score = questionsByTest.stream()
                    .map(question -> getScore(question, questionsAndAnswer))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            final var studentTest = studentTestRepository.findStudentTestByStudentAndTest(student.get(), test.get());
            studentTest.setScore(score.intValue());
            return studentTestRepository.save(studentTest);
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    private BigDecimal getScore(final Question question, final Map<Long, String> questionsAndAnswer) {
        final var answer = questionsAndAnswer.get(question.getId());
        if (Objects.nonNull(answer) && Objects.equals(question.getCorrectAnswer(), answer)) {
            return BigDecimal.ONE;
        }
        return BigDecimal.ZERO;
    }
}
