package com.siga.sus.authorization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para autenticação do usuário")
public class LoginRequest {

    @Schema(description = "Nome de usuário", example = "admin", required = true)
    private String username;

    @Schema(description = "Senha do usuário", example = "123456", required = true)
    private String password;
}