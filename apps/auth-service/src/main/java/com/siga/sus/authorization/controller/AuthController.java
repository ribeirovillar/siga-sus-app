package com.siga.sus.authorization.controller;

import com.siga.sus.authorization.dto.LoginRequest;
import com.siga.sus.authorization.dto.LoginResponse;
import com.siga.sus.authorization.dto.RegisterRequest;
import com.siga.sus.authorization.dto.RegisterResponse;
import com.siga.sus.authorization.mapper.UserMapper;
import com.siga.sus.authorization.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@Slf4j
public class AuthController implements AuthApi {

    AuthService authService;
    UserMapper userMapper;


    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        log.info("Login attempt for username: {}", request.getUsername());
        return ResponseEntity.ok(userMapper.map(authService.authenticate(userMapper.map(request))));
    }

    @Override
    public ResponseEntity<RegisterResponse> register(RegisterRequest request) {
        log.info("Registration attempt for username: {}", request.getUsername());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.map(authService.register(userMapper.map(request))));
    }
}
