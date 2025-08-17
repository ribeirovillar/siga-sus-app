package com.siga.sus.authorization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta do registro de usuário")
public class RegisterResponse {

    @Schema(description = "ID único do usuário criado", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID userId;

    @Schema(description = "Nome de usuário registrado", example = "joao.silva")
    private String username;
}
