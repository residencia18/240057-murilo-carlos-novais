package com.residencia18.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.residencia18.api.entity.User;
import com.residencia18.api.exception.GenerationTokenException;
import com.residencia18.api.exception.VerifyingTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;      // Chave secreta para gerar o token.
    private static final String ISSUER = "API Avaliacao";   // Identificação da API que gera o token.
    private static final Set<String> invalidTokens = new HashSet<>();  // Conjunto para armazenar tokens inválidos.

    // Gera um token para recuperação de senha.
    public String generateTokenRecovery(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("id", user.getId())
                    .withClaim("email", user.getEmail())
                    .withExpiresAt(expirationRecovery())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new GenerationTokenException(exception.getMessage());
        }
    }

    // Verifica se o token é válido.
    public boolean isValidToken(String tokenJWT) {
        if (invalidTokens.contains(tokenJWT)) {
            return false;
        }
        try {
            var algorithm = Algorithm.HMAC256(secret);
            JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT);
            return true;
        } catch (JWTVerificationException exception){
            return false;
        }
    }

    // Obtém o e-mail associado ao token.
    public String getEmail(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getClaim("email").asString();
        } catch (JWTVerificationException exception){
            throw new VerifyingTokenException(exception.getMessage());
        }
    }

    // Obtém o ID associado ao token.
    public Long getId(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getClaim("id").asLong();
        } catch (JWTVerificationException exception){
            throw new VerifyingTokenException(exception.getMessage());
        }
    }

    // Define o tempo de expiração para recuperação de senha (15 minutos a partir de agora).
    private Instant expirationRecovery() {
        return LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-03:00"));
    }

    // Invalida o token especificado, adicionando-o ao conjunto de tokens inválidos.
    public void invalidateToken(String tokenJWT) {
        invalidTokens.add(tokenJWT);
    }
}
