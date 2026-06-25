package com.exam.smartpark.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public BaseException(HttpStatus httpStatus, String message) {
        super(message);
        this.status = httpStatus;
    }
}
