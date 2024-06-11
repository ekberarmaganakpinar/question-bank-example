/* (C)2024 */
package com.questionbank.questionbank.mapper;

import com.questionbank.questionbank.dto.*;
import com.questionbank.questionbank.entity.Question;
import com.questionbank.questionbank.entity.Student;
import com.questionbank.questionbank.entity.StudentTest;
import com.questionbank.questionbank.entity.Test;

public abstract class DtoMapper {

    public static StudentQueryDto studentEntityToDtoMapper(final Student student) {
        return StudentQueryDto.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .studentNumber(student.getStudentNumber())
                .build();
    }

    public static StudentReadDto studentEntityToDtoMapperAllData(final Student student) {
        return StudentReadDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .studentNumber(student.getStudentNumber())
                .build();
    }

    public static Student studentDtoToEntityMapper(final StudentQueryDto studentDto) {
        final var student = new Student();
        student.setStudentNumber(studentDto.getStudentNumber());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        return student;
    }

    public static TestQueryDto testEntityToDtoMapper(final Test test) {
        return TestQueryDto.builder().testName(test.getTestName()).build();
    }

    public static Test testDtoToEntityMapper(final TestQueryDto testDto) {
        final var test = new Test();
        test.setTestName(testDto.getTestName());
        return test;
    }

    public static QuestionQueryDto questionEntityToDtoMapper(final Question question) {
        return QuestionQueryDto.builder()
                .questionText(question.getQuestionText())
                .correctAnswer(question.getCorrectAnswer())
                .test(question.getTest())
                .options(question.getOptions())
                .build();
    }

    public static QuestionReadDto questionEntityToDtoMapperAllData(final Question question) {
        return QuestionReadDto.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .correctAnswer(question.getCorrectAnswer())
                .test(question.getTest())
                .options(question.getOptions())
                .build();
    }

    public static Question questionDtoToEntityMapper(final QuestionWriteDto questionDto, final Test test) {
        final var question = new Question();
        question.setQuestionText(questionDto.getQuestionText());
        question.setTest(test);
        question.setOptions(questionDto.getOptions());
        question.setCorrectAnswer(questionDto.getCorrectAnswer());
        return question;
    }

    public static StudentTestQueryDto studentTestEntityToDtoMapper(final StudentTest studentTest) {
        return StudentTestQueryDto.builder()
                .score(studentTest.getScore())
                .test(studentTest.getTest())
                .student(studentTest.getStudent())
                .build();
    }

    public static StudentTestReadDto studentTestEntityToDtoMapperAllData(final StudentTest studentTest) {
        return StudentTestReadDto.builder()
                .id(studentTest.getId())
                .score(studentTest.getScore())
                .test(studentTest.getTest())
                .student(studentTest.getStudent())
                .build();
    }

    public static StudentTest studentTestDtoToEntityMapper(
            final StudentTestWriteDto studentTestWriteDto, final Student student, final Test test) {
        final var studentTest = new StudentTest();
        studentTest.setStudent(student);
        studentTest.setTest(test);
        studentTest.setScore(studentTestWriteDto.getScore());
        return studentTest;
    }
}
