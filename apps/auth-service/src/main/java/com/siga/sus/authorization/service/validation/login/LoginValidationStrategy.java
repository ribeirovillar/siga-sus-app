package com.siga.sus.authorization.service.validation.login;

import com.siga.sus.authorization.model.User;

public interface LoginValidationStrategy {
    void validate(User user);
}
