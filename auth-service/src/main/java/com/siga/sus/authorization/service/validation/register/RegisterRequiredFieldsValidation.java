package com.siga.sus.authorization.service.validation.register;

import com.siga.sus.authorization.exception.AuthException;
import com.siga.sus.authorization.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Component
public class RegisterRequiredFieldsValidation implements RegisterValidationStrategy {
    @Override
    public void validate(User user) {
        if (user == null) {
            throw AuthException.invalidRequest("user");
        }
        if (!StringUtils.hasText(user.getName())) {
            throw AuthException.invalidRequest("name");
        }
        if (!StringUtils.hasText(user.getUsername())) {
            throw AuthException.invalidRequest("username");
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw AuthException.invalidRequest("password");
        }
        if (Objects.isNull(user.getRole())) {
            throw AuthException.invalidRequest("role");
        }
    }
}
