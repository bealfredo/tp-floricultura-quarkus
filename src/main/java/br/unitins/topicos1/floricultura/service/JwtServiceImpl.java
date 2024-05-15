package br.unitins.topicos1.floricultura.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import br.unitins.topicos1.floricultura.dto.UsuarioResponseDTO;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @Override
    public String generateJwt(UsuarioResponseDTO dto) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);

        // exemplo para teste
        Set<String> roles = new HashSet<String>();

        // switch (dto.tipoUsuario()) {
        //     case ADMIN:
        //         roles.add("Admin");
        //         break;
        //     case CLIENTE:
        //         roles.add("Cliente");
        //         break;
        //     case TEST:
        //         roles.add("Test");
        //         break;
        // }

        return Jwt.issuer("unitins-jwt")
            .subject(dto.login())
            .groups(roles)
            .expiresAt(expiryDate)
            .sign();
    }
    
}
