package br.unitins.topicos1.floricultura.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import br.unitins.topicos1.floricultura.dto.UsuarioResponseDTO;
import br.unitins.topicos1.floricultura.model.TipoPerfil;
import br.unitins.topicos1.floricultura.model.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @Override
    public String generateJwt(Usuario usuario, TipoPerfil tipoPerfil) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);

        // exemplo para teste
        Set<String> roles = new HashSet<String>();

        switch (tipoPerfil) {
            case OWNER:
                roles.add(TipoPerfil.OWNER.name());
                break;
            case EMPLOYEE:
                roles.add(TipoPerfil.EMPLOYEE.name());
                break;
            case CUSTOMER:
                roles.add(TipoPerfil.CUSTOMER.name());
                break;
            case DELIVERYMAN:
                roles.add(TipoPerfil.DELIVERYMAN.name());
                break;
            default:
                break;
        }

        return Jwt.issuer("unitins-jwt")
            .subject(usuario.getLogin())
            .groups(roles)
            .expiresAt(expiryDate)
            .sign();
    }
    
}
