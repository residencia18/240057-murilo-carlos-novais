import java.time.Instant;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

// Provedor JWT para geração de tokens JWT
@Service
@RequiredArgsConstructor
public class JwtProvider {
    
    // Encoder JWT para codificar tokens
    private final JwtEncoder jwtEncoder;
    
    // Tempo de expiração do token em segundos
    public static final Long EXPIRATION_TIME_IN_SECONDS = 3600L;

    // Método para gerar um token JWT com base na autenticação do usuário
    public String generateToken(Authentication authentication) {
        // Obtém o principal (usuário autenticado) da autenticação
        User principal = (User) authentication.getPrincipal();
        
        // Obtém a data e hora atual
        var now = Instant.now();
        
        // Constrói as reivindicações (claims) do token JWT
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(EXPIRATION_TIME_IN_SECONDS))
                .subject(principal.getUsername())
                .claim("scope", "ROLE_USER")
                .build();
        
        // Codifica as reivindicações em um token JWT e retorna o valor do token
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
