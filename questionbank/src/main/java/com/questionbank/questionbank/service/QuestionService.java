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

import com.questionbank.questionbank.dto.QuestionWriteDto;
import com.questionbank.questionbank.entity.Question;
import com.questionbank.questionbank.exception.QuestionBankNotFoundException;
import com.questionbank.questionbank.mapper.DtoMapper;
import com.questionbank.questionbank.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TestService testService;
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);

    @Cacheable(value = "cache3", key = "'#allQuestions'")
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    @Cacheable(value = "cache3", key = "#id")
    public Optional<Question> findQuestionById(final Long id) {
        return questionRepository.findById(id);
    }

    @Transactional
    @CachePut(value = "cache3", key = "#result.id")
    @CacheEvict(value = "cache3", key = "'#allQuestions'")
    public Question createQuestion(final QuestionWriteDto questionDto) {
        try {
            final var testOpt = testService.findTestById(questionDto.getTestId());
            if (testOpt.isEmpty()) {
                return null;
            }
            final var test = testOpt.get();
            final var questionEntity = DtoMapper.questionDtoToEntityMapper(questionDto, test);
            return questionRepository.save(questionEntity);

        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    @Transactional
    @CachePut(value = "cache3", key = "#result.id")
    @CacheEvict(value = "cache3", key = "'#allQuestions'")
    public Question updateQuestion(final Map<String, Object> fields, final Long id) {
        final var questionOpt = questionRepository.findById(id);
        if (questionOpt.isEmpty()) {
            return null;
        }
        final var question = questionOpt.get();
        try {
            fields.forEach((key, value) -> {
                final var field = ReflectionUtils.findField(Question.class, key);
                if (Objects.nonNull(field)) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, question, getUpdateValue(key, value));
                }
            });
            return questionRepository.save(question);
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    private Object getUpdateValue(final String key, final Object value) {
        if (key.equals("test")) {
            final var testOpt = testService.findTestById(Long.valueOf(value.toString()));
            if (testOpt.isEmpty()) {
                throw new QuestionBankNotFoundException("Test is not found!!");
            }
            return testOpt.get();
        }
        return value;
    }

    @Transactional
    @CacheEvict(value = "cache3", key = "'#allQuestions'")
    public Question deleteQuestion(final Long id) {
        if (questionRepository.findById(id).isEmpty()) {
            return null;
        }
        try {
            questionRepository.deleteById(id);
            return new Question();
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    protected List<Question> getQuestionsByTest(final Long testId) {
        return testService
                .findTestById(testId)
                .map(questionRepository::findQuestionByTest)
                .orElse(Collections.emptyList());
    }
}
