/* (C)2024 */
package com.questionbank.questionbank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentQueryDto {
    private String firstName;
    private String lastName;
    private String studentNumber;
}
