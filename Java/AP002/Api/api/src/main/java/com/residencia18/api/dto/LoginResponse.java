package com.residencia18.api.dto;

// DTO (Data Transfer Object) para resposta de login
public record LoginResponse(
        // O token de autenticação gerado após o login bem-sucedido
        String token
) { }
