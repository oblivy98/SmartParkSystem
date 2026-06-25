package com.exam.smartpark.exception.model;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record StandardizedErrorResponse (
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {
    public static StandardizedErrorResponse of(HttpStatus status,
                                               String message) {
        return new StandardizedErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );
    }
}
