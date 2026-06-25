package com.exam.smartpark.exception.type;

import com.exam.smartpark.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UnprocessableContentException extends BaseException {
    public UnprocessableContentException(String message) {
        super(HttpStatus.UNPROCESSABLE_CONTENT, message);
    }
}
