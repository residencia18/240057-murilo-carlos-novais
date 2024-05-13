package com.residencia18.api.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.residencia18.api.dto.LoginRequest;
import com.residencia18.api.dto.LoginResponse;
import com.residencia18.api.dto.RegisterRequest;
import com.residencia18.api.dto.RegisterResponse;
import com.residencia18.api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    // Endpoint para registro de usuários
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        // Chama o serviço de autenticação para realizar o registro
        RegisterResponse response = authenticationService.register(registerRequest);
        // Retorna uma resposta com o resultado do registro
        return ResponseEntity.ok().body(response);
    }

    // Endpoint para login de usuários
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        // Chama o serviço de autenticação para realizar o login
        LoginResponse response = authenticationService.login(loginRequest);
        // Retorna uma resposta com o resultado do login
        return ResponseEntity.ok(response);
    }
}

