package com.residencia18.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// DTO (Data Transfer Object) para solicitação de redefinição de senha
public record ChangePasswordRequest(
        // O token de redefinição de senha
        @NotBlank(message = "The token is required!")  // A anotação @NotBlank garante que o token não seja vazio
        String token,

        // A nova senha a ser definida
        @NotBlank(message = "The new password is required!")  // A anotação @NotBlank garante que a senha não seja vazia
        @Size(min = 8, message = "Password must be at least 8 characters!")  // A senha deve ter pelo menos 8 caracteres
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter!")  // Deve conter pelo menos uma letra minúscula
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter!")  // Deve conter pelo menos uma letra maiúscula
        @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit!")  // Deve conter pelo menos um dígito
        @Pattern(regexp = ".*[^a-zA-Z0-9].*", message = "Password must contain at least one special character!")  // Deve conter pelo menos um caractere especial
        @Pattern(regexp = "^\\S*$", message = "Password must not contain whitespace!")  // A senha não deve conter espaços em branco
        String newPassword
) { }
