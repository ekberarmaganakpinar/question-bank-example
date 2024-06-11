/* (C)2024 */
package com.questionbank.questionbank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.questionbank.questionbank.entity.Student;
import com.questionbank.questionbank.entity.StudentTest;
import com.questionbank.questionbank.entity.Test;

public interface StudentTestRepository extends JpaRepository<StudentTest, Long> {
    List<StudentTest> findByStudent(Student student);

    StudentTest findStudentTestByStudentAndTest(Student student, Test test);
}
