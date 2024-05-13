package com.residencia18.api.service;

import com.residencia18.api.dto.ChangePasswordRequest;
import com.residencia18.api.entity.User;
import com.residencia18.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {
    
    // Injeta o repositório de usuários para acessar os dados do usuário
    @Autowired
    private UserRepository userRepository;
    
    // Injeta o serviço de token para manipulação dos tokens de redefinição de senha
    @Autowired
    private TokenService tokenService;
    
    // Injeta o codificador de senha para criptografar a nova senha do usuário
    @Autowired
    private PasswordEncoder encoder;

    // Método para redefinir a senha do usuário
    public void resetPassword(ChangePasswordRequest request) {
        // Verifica se o token de redefinição de senha é válido
        if(!tokenService.isValidToken(request.token()))
            throw new InvalidBearerTokenException("Invalid or expired token!");

        // Obtém o ID do usuário a partir do token
        Long userId = tokenService.getId(request.token());

        // Busca o usuário pelo ID ou lança uma exceção se não encontrado
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found!"));
        
        // Define a nova senha criptografada para o usuário
        user.setPassword(encoder.encode(request.newPassword()));

        // Salva as alterações na senha do usuário
        userRepository.saveAndFlush(user);
        
        // Invalida o token de redefinição de senha após o uso
        tokenService.invalidateToken(request.token());
    }
}
