package com.residencia18.api.mapper;

import org.springframework.stereotype.Service;

import com.residencia18.api.dto.RegisterRequest;
import com.residencia18.api.entity.User;

// Classe responsável por mapear objetos entre DTOs e entidades relacionadas a usuários
@Service
public class UserMapper {

    // Método para mapear um objeto RegisterRequest para a entidade User
    public User fromRegisterRequest(RegisterRequest registerRequest) {
        // Retorna uma nova instância de User baseada nos dados fornecidos em RegisterRequest
        return new User(
                null, // O id será gerado automaticamente pelo banco de dados
                registerRequest.email(), // Obtém o email do RegisterRequest
                registerRequest.username(), // Obtém o username do RegisterRequest
                registerRequest.password(), // Obtém a senha do RegisterRequest
                "ROLE_USER" // Define o papel/role como ROLE_USER
        );
    }
}
