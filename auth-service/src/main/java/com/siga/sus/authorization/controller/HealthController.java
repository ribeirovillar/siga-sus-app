package com.siga.sus.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check", description = "Endpoints para verificação da saúde da aplicação")
public class HealthController {

    @GetMapping
    @Operation(summary = "Verificar saúde da aplicação", description = "Retorna o status da aplicação")
    public String health() {
        return "OK - Authorization Service is running";
    }
}