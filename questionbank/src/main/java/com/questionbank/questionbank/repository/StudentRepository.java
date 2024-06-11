/* (C)2024 */
package com.questionbank.questionbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.questionbank.questionbank.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {}
