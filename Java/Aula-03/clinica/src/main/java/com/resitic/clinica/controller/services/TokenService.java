package com.resitic.clinica.controller.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.resitic.clinica.model.Usuario;

@Service
public class TokenService {

	private final String secret;

	public TokenService(@Value("${api.security.token.secret}") String secret) {
		this.secret = secret;
	}

	public String generateToken(Usuario usuario) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			Date expirationDate = Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC));
			return JWT.create()
					.withIssuer("API Voll.med")
					.withSubject(usuario.getLogin())
					.withExpiresAt(expirationDate)
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Erro ao gerar token JWT", exception);
		}
	}

	public String getSubject(String tokenJWT) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("API Voll.med")
					.build()
					.verify(tokenJWT)
					.getSubject();
		} catch (JWTVerificationException exception) {
			throw new RuntimeException("Erro ao verificar token JWT", exception);
		}
	}
}
