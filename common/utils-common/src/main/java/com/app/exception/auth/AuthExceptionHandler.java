package com.app.exception.auth;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthExceptionHandler extends RuntimeException {

    private final HttpStatus status;

    public AuthExceptionHandler(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static AuthExceptionHandler invalidCredentials() {
        return new AuthExceptionHandler("Email ou senha inválidos.", HttpStatus.UNAUTHORIZED);
    }

    public static AuthExceptionHandler userAlreadyExists() {
        return new AuthExceptionHandler("E-mail já cadastrado.", HttpStatus.CONFLICT);
    }

    public static AuthExceptionHandler unauthorized() {
        return new AuthExceptionHandler("Acesso não autorizado.", HttpStatus.UNAUTHORIZED);
    }

}
