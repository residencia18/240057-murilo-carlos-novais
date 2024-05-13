package com.residencia18.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {
    
    // Endpoint para exibir o painel de controle
    @GetMapping("/dashboard")
    public String dashboard() {
        String username = "Guest"; // Define um nome de usuário padrão como "Guest" caso não haja usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Verifica se há uma autenticação válida
        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName(); // Obtém o nome de usuário da autenticação
        }
        // Retorna uma mensagem de saudação personalizada com o nome de usuário
        return "Hello " + username;
    }
}
