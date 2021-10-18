package com.assessment.taskmanagement.exception;

public class BackUpServerException extends AppException {
    public BackUpServerException(String errorType, String errorCode, String message) {
        super(errorType, errorCode, message);
    }

    public BackUpServerException(String message) {
        super(message);
    }
}
