/* (C)2024 */
package com.questionbank.questionbank.dto;

import com.questionbank.questionbank.entity.Student;
import com.questionbank.questionbank.entity.Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentTestQueryDto {
    private Student student;
    private Test test;
    private int score;
}
