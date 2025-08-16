package com.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServicesExceptionHandler extends RuntimeException {

    private final HttpStatus status;

    public ServicesExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static ServicesExceptionHandler handleException(String message) {
        return new ServicesExceptionHandler(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
