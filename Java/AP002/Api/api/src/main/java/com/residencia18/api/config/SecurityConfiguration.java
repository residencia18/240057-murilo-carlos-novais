package com.residencia18.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtConfigProperties jwtConfigProperties;

    // Configuração do filtro de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())  // Configuração padrão para lidar com CORS (Cross-Origin Resource Sharing)
                .csrf(csrf -> csrf.disable())  // Desativa a proteção CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()    // Todas as requisições de login e registro são permitidas.
                        .requestMatchers("/api/password-recovery").permitAll()  // Todas as requisições de recuperação de senha são permitidas.
                        .requestMatchers("/api/password-reset").permitAll()     // Todas as requisições de redefinição de senha são permitidas.
                        .requestMatchers("v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()  // Permite acesso à documentação do Swagger
                        .anyRequest().authenticated())  // Todas as outras requisições precisam de autenticação
                .oauth2ResourceServer(oAuth2ResourceServerConfigurer
                        -> oAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()))  // Configura o servidor de recursos OAuth2 para usar JWT para autenticação
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Configura a política de gerenciamento de sessões para não criar sessões
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())  // Define o ponto de entrada para lidar com erros de autenticação
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))  // Define o manipulador para lidar com erros de acesso negado
                .build();
    }

    // Configuração do codificador de senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuração do decodificador JWT
    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(jwtConfigProperties.getPublicKey()).build();
    }

    // Configuração do codificador JWT
    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(jwtConfigProperties.getPublicKey())
                .privateKey(jwtConfigProperties.getPrivateKey())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    // Configuração do gerenciador de autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
