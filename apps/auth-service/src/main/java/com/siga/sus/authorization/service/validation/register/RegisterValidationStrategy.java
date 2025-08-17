package com.siga.sus.authorization.service.validation.register;

import com.siga.sus.authorization.model.User;

public interface RegisterValidationStrategy {
    void validate(User user);
}
