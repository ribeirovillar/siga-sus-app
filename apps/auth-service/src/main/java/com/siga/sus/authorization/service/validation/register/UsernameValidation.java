package com.siga.sus.authorization.service.validation.register;

import com.siga.sus.authorization.exception.AuthException;
import com.siga.sus.authorization.model.User;
import com.siga.sus.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class UsernameValidation implements RegisterValidationStrategy {

    UserRepository userRepository;

    @Override
    public void validate(User user) {
        if (StringUtils.hasText(user.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
            throw AuthException.userAlreadyExists();
        }
    }
}
