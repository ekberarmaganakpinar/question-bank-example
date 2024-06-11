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
public class StudentReadDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String studentNumber;
}
