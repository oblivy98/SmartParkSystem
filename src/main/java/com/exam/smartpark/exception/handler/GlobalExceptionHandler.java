package com.exam.smartpark.exception.handler;

import com.exam.smartpark.exception.model.StandardizedErrorResponse;
import com.exam.smartpark.exception.type.DuplicateKeyException;
import com.exam.smartpark.exception.type.ResourceNotFoundException;
import com.exam.smartpark.exception.type.UnprocessableContentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardizedErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(
                StandardizedErrorResponse.of(   ex.getStatus(),
                                                ex.getMessage()),
                ex.getStatus()
        );
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<StandardizedErrorResponse> handleDuplicateKey(DuplicateKeyException ex) {
        return new ResponseEntity<>(
                StandardizedErrorResponse.of(   ex.getStatus(),
                                                ex.getMessage()),
                ex.getStatus()
        );
    }

    @ExceptionHandler(UnprocessableContentException.class)
    public ResponseEntity<StandardizedErrorResponse> handleUnprocessableContent(UnprocessableContentException ex) {
        return new ResponseEntity<>(
                StandardizedErrorResponse.of(   ex.getStatus(),
                        ex.getMessage()),
                ex.getStatus()
        );
    }
}
