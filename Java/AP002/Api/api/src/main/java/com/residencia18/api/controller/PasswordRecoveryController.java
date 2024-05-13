package com.residencia18.api.controller;

import com.residencia18.api.dto.RecoveryRequest;
import com.residencia18.api.entity.User;
import com.residencia18.api.messages.EmailMessages;
import com.residencia18.api.repository.UserRepository;
import com.residencia18.api.service.EmailService;
import com.residencia18.api.service.TokenService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PasswordRecoveryController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    // Endpoint para solicitação de recuperação de senha
    @PostMapping("/password-recovery")
    public ResponseEntity<?> passwordRecovery(@RequestBody @Valid RecoveryRequest request) throws MessagingException {

        // Procura o usuário pelo e-mail fornecido na solicitação
        User user = userRepository.findByEmail(request.email());
        // Se o usuário não for encontrado, retorna uma resposta de erro
        if(user == null) return ResponseEntity.badRequest().body("User not found!");

        // Gera um token de recuperação para o usuário
        String token = tokenService.generateTokenRecovery(user);
        // Constrói a URL para o formulário de recuperação de senha
        String url = "http://localhost:4200/recover-password/?token=" + token;

        // Envia um e-mail de recuperação de senha para o usuário
        emailService.sendEmail(request.email(), EmailMessages.RECOVERY_TITLE, EmailMessages.messageToRecovery(user, url));

        // Retorna uma resposta de sucesso
        return ResponseEntity.ok().body("Email sent successfully!");
    }
}
