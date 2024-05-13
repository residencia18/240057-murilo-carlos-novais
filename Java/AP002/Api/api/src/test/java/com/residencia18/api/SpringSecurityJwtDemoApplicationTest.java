package com.residencia18.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.residencia18.api.dto.LoginResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SpringSecurityJwtDemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    // Método de teste para verificar se o contexto da aplicação é carregado corretamente
    void contextLoads() throws Exception {

        // Dados de teste
        String user = "user_test_6";
        String password = "passwordABC123";
        String email = "user_test_6@gmail.com";

        // Construção do corpo da requisição para registro de usuário
        var registerRequest = String.format("""
                                {
                                    "username":"%s",
                                    "password":"%s",
                                    "email":"%s"
                                }
                                """, user, password, email);

        // Register a user
        // Chamada POST para registrar um usuário
        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content(registerRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message",
                        org.hamcrest.Matchers.is("User registered successfully")));

        // Construção do corpo da requisição para login
        var loginRequest = String.format("""
                                {
                                    "username":"%s",
                                    "password":"%s"
                                }
                                """, user, password);
        // Login with the registered user and get a token
        // Chamada POST para login e obtenção de um token
        var responseString = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token",
                        org.hamcrest.Matchers.notNullValue()))
                .andReturn()
                .getResponse().getContentAsString();

        // Mapeamento da resposta do login para um objeto LoginResponse
        var loginResponse = new ObjectMapper().readValue(responseString, LoginResponse.class);

        // Use the token to access a protected resource
        // Utilização do token para acessar um recurso protegido
        mockMvc.perform(get("/api/dashboard")
                        .header("Authorization", "Bearer " + loginResponse.token()))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.is("Hello " + user)));
    }

}
