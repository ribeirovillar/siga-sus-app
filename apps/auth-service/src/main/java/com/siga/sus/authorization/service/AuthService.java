package com.siga.sus.authorization.service;

import com.siga.sus.authorization.exception.AuthException;
import com.siga.sus.authorization.model.User;
import com.siga.sus.authorization.repository.UserRepository;
import com.siga.sus.authorization.security.JwtTokenProvider;
import com.siga.sus.authorization.service.validation.login.LoginValidationStrategy;
import com.siga.sus.authorization.service.validation.register.RegisterValidationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider jwtTokenProvider;
    List<RegisterValidationStrategy> registerValidations;
    List<LoginValidationStrategy> loginValidations;

    public String authenticate(User request) {
        loginValidations.forEach(strategy -> strategy.validate(request));

        return userRepository.findByUsername(request.getUsername())
                .map(user ->
                        jwtTokenProvider.generateToken(user.getUsername(), user.getRole().toString())
                )
                .orElseThrow(AuthException::failGenerateToken);
    }

    public User register(User user) {
        registerValidations.forEach(strategy -> strategy.validate(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}