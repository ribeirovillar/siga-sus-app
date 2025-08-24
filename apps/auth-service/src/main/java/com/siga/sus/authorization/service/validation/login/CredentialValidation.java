package com.siga.sus.authorization.service.validation.login;

import com.siga.sus.authorization.exception.AuthException;
import com.siga.sus.authorization.model.User;
import com.siga.sus.authorization.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CredentialValidation implements LoginValidationStrategy {

    UserRepository userRepository;

    @Override
    public void validate(User request) {
        if (StringUtils.hasText(request.getUsername())) {
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(AuthException::invalidCredentials);
            if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
                throw AuthException.invalidCredentials();
            }
        }
    }
}
