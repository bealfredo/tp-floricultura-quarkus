package br.unitins.topicos1.floricultura.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import br.unitins.topicos1.floricultura.model.TipoPerfil;
import br.unitins.topicos1.floricultura.model.Usuario;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @Inject
    JWTParser jwtParser;

    @Override
    public String generateJwt(Usuario usuario, TipoPerfil tipoPerfil) {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(EXPIRATION_TIME);

        Set<String> roles = new HashSet<>();
        roles.add(tipoPerfil.name());

        return Jwt.issuer("unitins-jwt")
            .subject(usuario.getLogin())
            .groups(roles)
            .expiresAt(expiryDate)
            .sign();
    }

    public String getTokenForTest() {
        Set<String> roles = new HashSet<>();
        roles.add(TipoPerfil.OWNER.name());

        return Jwt.issuer("unitins-jwt")
            .subject("test")
            .groups(roles)
            .expiresAt(Instant.now().plus(EXPIRATION_TIME))
            .sign();
    }
}
