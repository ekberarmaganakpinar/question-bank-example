/* (C)2024 */
package com.questionbank.questionbank.dto;

import java.io.Serializable;

import com.questionbank.questionbank.entity.Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionQueryDto implements Serializable {
    private Test test;
    private String questionText;
    private String correctAnswer;
    private String options;
}
