package com.siga.sus.authorization.service.validation.login;

import com.siga.sus.authorization.exception.AuthException;
import com.siga.sus.authorization.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class LoginRequiredFieldsValidation implements LoginValidationStrategy {
    @Override
    public void validate(User user) {
        if (!StringUtils.hasText(user.getUsername())) {
            throw AuthException.invalidRequest("username");
        }
        if (!StringUtils.hasText(user.getPassword())) {
            throw AuthException.invalidRequest("password");
        }
    }
}
