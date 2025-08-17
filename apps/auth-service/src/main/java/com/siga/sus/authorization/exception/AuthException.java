package com.siga.sus.authorization.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends RuntimeException {

    private final HttpStatus status;

    public AuthException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static AuthException invalidRequest(String field) {
        return new AuthException("Atributo: '" + field + "' é obrigatório", HttpStatus.BAD_REQUEST);
    }

    public static AuthException invalidCredentials() {
        return new AuthException("Username ou senha inválidos.", HttpStatus.UNAUTHORIZED);
    }

    public static AuthException userAlreadyExists() {
        return new AuthException("Username já cadastrado.", HttpStatus.CONFLICT);
    }

    public static AuthException failGenerateToken() {
        return new AuthException("Falha ao gerar token", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
