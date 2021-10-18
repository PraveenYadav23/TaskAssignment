package com.assessment.taskmanagement.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"cause", "stack-trace", "suppressed", "localizedMessage"})
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorType;
    private String errorCode;
    private String message;

    public AppException(String errorType, String errorCode, String message){
        super(message);
        this.errorType = errorType;
        this.errorCode = errorCode;
        this.message = message;
    }

    public AppException(String message) {
        super(message);
        this.message = message;
    }
}
