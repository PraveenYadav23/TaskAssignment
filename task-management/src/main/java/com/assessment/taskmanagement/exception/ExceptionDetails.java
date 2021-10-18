package com.assessment.taskmanagement.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionDetails {

    private HttpStatus status;
    private Object message;
    private String errorType;

    public ExceptionDetails(HttpStatus status, Object message, String errorType) {
        this.status = status;
        this.message = message;
        this.errorType = errorType;
    }
}
