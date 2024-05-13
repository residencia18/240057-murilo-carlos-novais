package com.residencia18.api.dto;

import jakarta.validation.constraints.NotBlank;

// DTO (Data Transfer Object) para solicitação de login
public record LoginRequest(
        // O nome de usuário fornecido no login
        @NotBlank(message = "Username is required!") // A anotação @NotBlank garante que o nome de usuário não seja vazio
        String username,

        // A senha fornecida no login
        @NotBlank(message = "Password is required!") // A anotação @NotBlank garante que a senha não seja vazia
        String password
) { }
