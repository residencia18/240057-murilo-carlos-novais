package com.residencia18.api.service;

import com.residencia18.api.entity.User;
import com.residencia18.api.exception.VerifyingTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.Random.class)
@DisplayName("Token Service Test")
class TokenServiceTest {
    @Autowired
    private TokenService tokenService;

    @Test
    @DisplayName("Cycle of using a token")
    void cycleOfUsingAToken() {
        try{
            // GIVEN
            // Criando um usuário para testar o ciclo de uso de um token
            User user = new User(null, "test@email.com", "userTest", "Teste@123", "ROLE_USER");
            // Gerando um token de recuperação de senha para o usuário
            String token = tokenService.generateTokenRecovery(user);

            // WHEN
            // Verificando se o token é válido
            boolean isValid = tokenService.isValidToken(token);

            // THEN
            // Verificando se o token é válido
            assertTrue(isValid);

            // WHEN
            // Obtendo o email e o ID associados ao token
            String email = tokenService.getEmail(token);
            Long id = tokenService.getId(token);

            // THEN
            // Verificando se o email e o ID associados ao token correspondem aos do usuário
            assertEquals(user.getEmail(), email);
            assertEquals(user.getId(), id);

            // WHEN
            // Invalidando o token
            tokenService.invalidateToken(token);

            // THEN
            // Verificando se o token foi invalidado
            isValid = tokenService.isValidToken(token);
            assertFalse(isValid);

        }catch (Exception exception){
            // Se ocorrer uma exceção inesperada, o teste falha
            fail("Undue Exception: " + exception.getMessage());
        }
    }

    @Test
    @DisplayName("Invalid Token")
    void invalidToken() {
        // GIVEN
        // Token inválido
        String invalidToken = "invalidToken";

        // WHEN
        // Verificando se o token é válido
        boolean isValid = tokenService.isValidToken(invalidToken);

        // THEN
        // Verificando se o token é inválido
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Invalid Token - Get Email")
    void invalidToken_GetEmail() {
        String email = null;
        try{
            // GIVEN
            // Token inválido
            String invalidToken = "invalidToken";

            // WHEN
            // Tentativa de obter o email associado ao token inválido
            email = tokenService.getEmail(invalidToken);
        }catch (VerifyingTokenException exception){
            // THEN
            // Verificando se a exceção foi lançada e se o email é nulo
            assertNull(email);
        }
    }

    @Test
    @DisplayName("Invalid Token - Get ID")
    void invalidToken_GetId() {
        Long id = null;
        try {
            // GIVEN
            // Token inválido
            String invalidToken = "invalidToken";

            // WHEN
            // Tentativa de obter o ID associado ao token inválido
            id = tokenService.getId(invalidToken);
        }catch (VerifyingTokenException exception){
            // THEN
            // Verificando se a exceção foi lançada e se o ID é nulo
            assertNull(id);
        }
    }

    @Test
    @DisplayName("Already Invalidated Token")
    void alreadyInvalidatedToken() {
        // GIVEN
        // Criando um usuário para testar um token que já foi invalidado
        User user = new User(null, "email@example.com", "username", "password", "ROLE_USER");
        // Gerando um token de recuperação de senha para o usuário
        String token = tokenService.generateTokenRecovery(user);
        // Invalidando o token
        tokenService.invalidateToken(token);

        // WHEN
        // Verificando se o token é válido
        boolean isValid = tokenService.isValidToken(token);

        // THEN
        // Verificando se o token é inválido
        assertFalse(isValid);
    }

}
