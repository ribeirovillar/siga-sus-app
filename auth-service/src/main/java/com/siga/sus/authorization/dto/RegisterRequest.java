package com.siga.sus.authorization.dto;

import com.siga.sus.authorization.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para registro de novo usuário")
public class RegisterRequest {

    @Schema(description = "Nome completo do usuário", example = "Dr. João Silva", required = true)
    private String name;

    @Schema(description = "Nome de usuário único", example = "joao.silva", required = true)
    private String username;

    @Schema(description = "Senha do usuário", example = "senha123", required = true)
    private String password;

    @Schema(description = "Papel do usuário no sistema", example = "DOCTOR", required = true,
            allowableValues = {"NURSE", "DOCTOR"})
    private Role role;
}
