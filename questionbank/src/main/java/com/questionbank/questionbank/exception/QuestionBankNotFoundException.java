/* (C)2024 */
package com.questionbank.questionbank.exception;

public class QuestionBankNotFoundException extends RuntimeException {
    public QuestionBankNotFoundException(final String errMessage) {
        super(errMessage);
    }
}
