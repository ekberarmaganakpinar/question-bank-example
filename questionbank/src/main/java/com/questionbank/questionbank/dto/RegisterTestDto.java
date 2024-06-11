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
public class RegisterTestDto {
    private Long studentId;
    private Long testId;
}
