/* (C)2024 */
package com.questionbank.questionbank.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private String questionText;
    private String correctAnswer;
    private String options;
}
