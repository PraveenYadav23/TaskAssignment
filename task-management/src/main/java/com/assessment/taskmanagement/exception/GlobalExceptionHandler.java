package com.assessment.taskmanagement.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ExceptionDetails exceptionDetails = new ExceptionDetails(HttpStatus.BAD_REQUEST,
                errors, "Validation Failed");

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {

        ExceptionDetails exceptionDetails = new ExceptionDetails(HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(), "Invalid Request");

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleTaskNotFoundException(TaskNotFoundException exception,
                                                                        WebRequest request){

        ExceptionDetails exceptionDetails = new ExceptionDetails(HttpStatus.BAD_REQUEST,
                exception.getMessage(), "Task Not Found");

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleUserNotFoundException(UserNotFoundException exception,
                                                                        WebRequest request){

        ExceptionDetails exceptionDetails = new ExceptionDetails(HttpStatus.BAD_REQUEST,
                exception.getMessage(), "User Not Found");

        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BackUpServerException.class)
    public ResponseEntity<ExceptionDetails> handleBackupNotFoundException(BackUpServerException exception,
                                                                        WebRequest request){

        ExceptionDetails exceptionDetails = new ExceptionDetails(HttpStatus.REQUEST_TIMEOUT,
                exception.getMessage(), "Backup Server Error");

        return new ResponseEntity<>(exceptionDetails, HttpStatus.REQUEST_TIMEOUT);
    }

}
