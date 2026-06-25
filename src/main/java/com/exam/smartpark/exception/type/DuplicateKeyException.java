package com.exam.smartpark.exception.type;

import com.exam.smartpark.exception.BaseException;
import org.springframework.http.HttpStatus;

public class DuplicateKeyException extends BaseException {
    public DuplicateKeyException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
