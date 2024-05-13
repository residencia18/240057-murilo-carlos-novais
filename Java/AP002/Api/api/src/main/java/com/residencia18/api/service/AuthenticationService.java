package com.residencia18.api.service;

import com.residencia18.api.dto.RegisterResponse;
import com.residencia18.api.exception.EmailAlreadyRegisteredException;
import com.residencia18.api.exception.UsernameAlreadyRegisteredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.residencia18.api.dto.LoginRequest;
import com.residencia18.api.dto.LoginResponse;
import com.residencia18.api.dto.RegisterRequest;
import com.residencia18.api.entity.User;
import com.residencia18.api.mapper.UserMapper;
import com.residencia18.api.repository.UserRepository;
import com.residencia18.api.security.JwtProvider;
import lombok.RequiredArgsConstructor;

// Serviço para operações de autenticação de usuário
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository; // Repositório de usuários
    private final PasswordEncoder passwordEncoder; // Codificador de senhas
    private final AuthenticationManager authenticationManager; // Gerenciador de autenticação
    private final JwtProvider jwtProvider; // Provedor JWT para geração de tokens JWT
    private final UserMapper userMapper; // Mapper para mapear objetos entre DTOs e entidades de usuário

    // Método para registrar um novo usuário
    public RegisterResponse register(RegisterRequest registerRequest) {
        // Verifica se o e-mail já está registrado
        if (userRepository.findByEmail(registerRequest.email()) != null)
            throw new EmailAlreadyRegisteredException();
        // Verifica se o nome de usuário já está registrado
        if (userRepository.findByUsername(registerRequest.username()) != null)
            throw new UsernameAlreadyRegisteredException();
        // Mapeia o objeto RegisterRequest para a entidade User
        User user = userMapper.fromRegisterRequest(registerRequest);
        // Codifica a senha do usuário antes de salvar no banco de dados
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Salva o usuário no banco de dados
        userRepository.save(user);
        // Retorna uma resposta indicando que o usuário foi registrado com sucesso
        return new RegisterResponse("User registered successfully");
    }

    // Método para autenticar um usuário e gerar um token JWT
    public LoginResponse login(LoginRequest loginRequest) {
        // Autentica o usuário usando o AuthenticationManager
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(),
                loginRequest.password()));
        // Gera um token JWT com base na autenticação do usuário
        String token = jwtProvider.generateToken(authenticate);
        // Retorna uma resposta contendo o token JWT
        return new LoginResponse(token);
    }
}
