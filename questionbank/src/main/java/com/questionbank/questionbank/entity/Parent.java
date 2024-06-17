/* (C)2024 */
package com.questionbank.questionbank.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
