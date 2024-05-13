package com.residencia18.api.validation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    // Lista de senhas comuns que serão rejeitadas
    private List<String> commonPasswords = Arrays.asList("123456","654321", "password","senha" ,"12345678","87654321");

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // Método de inicialização, não utilizado neste validador
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Verifica se o valor da senha é nulo
        if (value == null) {
            return false;
        }
        
        // Valida se a senha possui pelo menos 8 caracteres, contém pelo menos uma letra maiúscula,
        // pelo menos uma letra minúscula, pelo menos um dígito, pelo menos um caractere especial,
        // e não está presente na lista de senhas comuns
        return value.length() >= 8 &&
                value.matches(".*[A-Z].*") &&
                value.matches(".*[a-z].*") &&
                value.matches(".*\\d.*") &&
                value.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*") &&
                !commonPasswords.contains(value);
    }
}
