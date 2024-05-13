package com.residencia18.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.residencia18.api.dto.ChangePasswordRequest;
import com.residencia18.api.entity.User;
import com.residencia18.api.repository.UserRepository;
import com.residencia18.api.service.PasswordResetService;
import com.residencia18.api.service.TokenService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;

@SpringBootTest
@DisplayName("Password Reset Service Test")
class PasswordResetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordResetService passwordResetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Reset Password - Successful")
    void resetPasswordTest1() {
        // GIVEN
        // Criação de uma solicitação de alteração de senha válida
        ChangePasswordRequest request = new ChangePasswordRequest("validToken", "newPassword");
        // Criação de um usuário para representar o usuário associado ao token
        User user = new User(1L, "email@example.com", "username", "oldPassword", "ROLE_USER");

        // Configuração do comportamento dos mocks
        when(tokenService.isValidToken(request.token())).thenReturn(true);
        when(tokenService.getId(request.token())).thenReturn(user.getId());
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.encode(request.newPassword())).thenReturn("encodedNewPassword");

        // WHEN
        // Chamada do método para redefinir a senha
        passwordResetService.resetPassword(request);

        // THEN
        // Verificação se os métodos dos mocks foram chamados corretamente e se a senha do usuário foi atualizada
        verify(tokenService, times(1)).isValidToken(request.token());
        verify(tokenService, times(1)).getId(request.token());
        verify(userRepository, times(1)).findById(user.getId());
        verify(passwordEncoder, times(1)).encode(request.newPassword());
        verify(userRepository, times(1)).saveAndFlush(user);
        verify(tokenService, times(1)).invalidateToken(request.token());
        assertEquals("encodedNewPassword", user.getPassword());
    }

    @Test
    @DisplayName("Reset Password - Invalid Token")
    void resetPasswordTest2() {
        // GIVEN
        // Criação de uma solicitação de alteração de senha com um token inválido
        ChangePasswordRequest request = new ChangePasswordRequest("invalidToken", "newPassword");

        // Configuração do comportamento do mock para indicar que o token não é válido
        when(tokenService.isValidToken(request.token())).thenReturn(false);

        // WHEN/THEN
        // Verificação se a exceção é lançada quando o token é inválido
        assertThrows(InvalidBearerTokenException.class, () -> passwordResetService.resetPassword(request));

        // Verificação se os métodos dos mocks foram chamados corretamente
        verify(tokenService, times(1)).isValidToken(request.token());
        verify(tokenService, never()).getId(any());
        verify(userRepository, never()).findById(any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).saveAndFlush(any());
        verify(tokenService, never()).invalidateToken(any());
    }

    @Test
    @DisplayName("Reset Password - User Not Found")
    void resetPasswordTest3() {
        // GIVEN
        // Criação de uma solicitação de alteração de senha com um token válido
        ChangePasswordRequest request = new ChangePasswordRequest("validToken", "newPassword");

        // Configuração do comportamento do mock para indicar que o usuário associado ao token não foi encontrado
        when(tokenService.isValidToken(request.token())).thenReturn(true);
        when(tokenService.getId(request.token())).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // WHEN/THEN
        // Verificação se a exceção é lançada quando o usuário não é encontrado
        assertThrows(EntityNotFoundException.class, () -> passwordResetService.resetPassword(request));

        // Verificação se os métodos dos mocks foram chamados corretamente
        verify(tokenService, times(1)).isValidToken(request.token());
        verify(tokenService, times(1)).getId(request.token());
        verify(userRepository, times(1)).findById(1L);
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).saveAndFlush(any());
        verify(tokenService, never()).invalidateToken(any());
    }
}
