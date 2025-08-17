package com.siga.sus.authorization.mapper;

import com.siga.sus.authorization.dto.LoginRequest;
import com.siga.sus.authorization.dto.LoginResponse;
import com.siga.sus.authorization.dto.RegisterRequest;
import com.siga.sus.authorization.dto.RegisterResponse;
import com.siga.sus.authorization.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User map(RegisterRequest request);

    @Mapping(target = "userId", source = "id")
    RegisterResponse map(User user);

    User map(LoginRequest request);

    @Mapping(target = "token", source = "token")
    LoginResponse map(String token);
}
