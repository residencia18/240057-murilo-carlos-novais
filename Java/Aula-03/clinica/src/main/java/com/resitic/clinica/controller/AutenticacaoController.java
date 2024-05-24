package com.resitic.clinica.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resitic.clinica.controller.DTO.AutenticacaoDTO;
import com.resitic.clinica.controller.DTO.TokenDTO;
import com.resitic.clinica.controller.services.TokenService;
import com.resitic.clinica.model.Usuario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	@PostMapping
	public ResponseEntity<TokenDTO> login(@RequestBody @Valid AutenticacaoDTO dto) {
		try {
			var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			String tokenJWT = tokenService.generateToken((Usuario) authentication.getPrincipal());
			return ResponseEntity.ok(new TokenDTO(tokenJWT));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
