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

import com.questionbank.questionbank.dto.TestQueryDto;
import com.questionbank.questionbank.entity.Test;
import com.questionbank.questionbank.mapper.DtoMapper;
import com.questionbank.questionbank.repository.TestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);

    @Cacheable(value = "cache1", key = "'#allTests'")
    public List<Test> getAllTest() {
        return testRepository.findAll();
    }

    @Cacheable(value = "cache1", key = "#id")
    public Optional<Test> findTestById(final Long id) {
        return testRepository.findById(id);
    }

    @Transactional
    @CachePut(value = "cache1", key = "#result.id")
    @CacheEvict(value = "cache1", key = "'#allTests'")
    public Test createTest(final TestQueryDto testDto) {
        try {
            final var test = DtoMapper.testDtoToEntityMapper(testDto);

            return testRepository.save(test);
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    @Transactional
    @CachePut(value = "cache1", key = "#result.id")
    @CacheEvict(value = "cache1", key = "'#allTests'")
    public Test updateTest(final Map<String, Object> fields, final Long id) {
        final var testOpt = testRepository.findById(id);
        if (testOpt.isEmpty()) {
            return null;
        }
        final var test = testOpt.get();
        try {
            fields.forEach((key, value) -> {
                final var field = ReflectionUtils.findField(Test.class, key);
                if (Objects.nonNull(field)) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, test, value);
                }
            });
            return testRepository.save(test);
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }

    @Transactional
    @CacheEvict(value = "cache1", key = "'#allTests'")
    public Test deleteTest(final Long id) {
        if (testRepository.findById(id).isEmpty()) {
            return null;
        }
        try {
            testRepository.deleteById(id);
            return new Test();
        } catch (final Exception e) {
            LOGGER.error(e.toString());
            return null;
        }
    }
}
