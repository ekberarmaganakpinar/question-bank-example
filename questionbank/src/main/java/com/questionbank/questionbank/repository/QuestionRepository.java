/* (C)2024 */
package com.questionbank.questionbank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.questionbank.questionbank.entity.Question;
import com.questionbank.questionbank.entity.Test;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findQuestionByTest(final Test test);
}
