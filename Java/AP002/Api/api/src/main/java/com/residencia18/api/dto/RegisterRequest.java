package com.residencia18.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// DTO (Data Transfer Object) para solicitação de registro de usuário
public record RegisterRequest(
        // O nome de usuário fornecido no registro
        @NotBlank(message = "Username is required!") // A anotação @NotBlank garante que o nome de usuário não seja vazio
        @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters!") // O tamanho do nome de usuário deve estar entre 5 e 15 caracteres
        String username,

        // A senha fornecida no registro
        @NotBlank(message = "Password is required!") // A anotação @NotBlank garante que a senha não seja vazia
        @Size(min = 8, message = "Password must be at least 8 characters!") // A senha deve ter pelo menos 8 caracteres
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter!") // Deve conter pelo menos uma letra minúscula
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter!") // Deve conter pelo menos uma letra maiúscula
        @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit!") // Deve conter pelo menos um dígito
        @Pattern(regexp = ".*[^a-zA-Z0-9].*", message = "Password must contain at least one special character!") // Deve conter pelo menos um caractere especial
        @Pattern(regexp = "^\\S*$", message = "Password must not contain whitespace!") // A senha não deve conter espaços em branco
        String password,

        // O endereço de e-mail fornecido no registro
        @NotBlank(message = "Email is required!") // A anotação @NotBlank garante que o e-mail não seja vazio
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email!") // A anotação @Pattern verifica se o e-mail tem um formato válido
        String email
) { }
