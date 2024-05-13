package com.residencia18.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// DTO (Data Transfer Object) para solicitação de recuperação de senha
public record RecoveryRequest(
        // O endereço de e-mail usado para solicitar a recuperação de senha
        @NotBlank(message = "Email is required!") // A anotação @NotBlank garante que o e-mail não seja vazio
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email!") // A anotação @Pattern verifica se o e-mail tem um formato válido
        String email
) { }
