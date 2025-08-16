package com.app.exception.auth;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RefreshTokenException extends RuntimeException {

    private final HttpStatus status;

    public RefreshTokenException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static RefreshTokenException expired() {
        return new RefreshTokenException("Refresh token expirado", HttpStatus.UNAUTHORIZED);
    }

    public static RefreshTokenException invalid() {
        return new RefreshTokenException("Refresh token inválido", HttpStatus.UNAUTHORIZED);
    }

}