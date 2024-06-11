/* (C)2024 */
package com.questionbank.questionbank.dto;

import com.questionbank.questionbank.entity.Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionReadDto {
    private Long id;
    private Test test;
    private String questionText;
    private String correctAnswer;
    private String options;
}
