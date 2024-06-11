/* (C)2024 */
package com.questionbank.questionbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class QuestionbankApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionbankApplication.class, args);
    }
}
