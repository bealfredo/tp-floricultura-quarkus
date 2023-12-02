package br.unitins.topicos1.floricultura.service;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.floricultura.dto.UsuarioResponseDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioUpdateSenhaDTO;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioLogadoServiceImpl implements UsuarioLogadoService {

    @Inject
    HashService hashService;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    UsuarioService usuarioService;

    @Inject
    JwtService jwtService;

    @Inject
    JsonWebToken jwt;

    @Override
    public UsuarioResponseDTO getUsuario() {
        String login = jwt.getSubject();

        Usuario usuario = usuarioRepository.findByLogin(login);

        return UsuarioResponseDTO.valueOf(usuario);
    }


    @Override
    @Transactional
    public void updateSenha(UsuarioUpdateSenhaDTO dto) {

        String login = jwt.getSubject();
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null) 
            throw new ValidationException("login", "Login inválido");

        String hashSenhaAntiga = hashService.getHashSenha(dto.senhaAntiga());

        if (!hashSenhaAntiga.equals(usuario.getSenha()))
            throw new ValidationException("senhaAntiga", "A senha informada está incorreta");

        String hashSenhaNova = hashService.getHashSenha(dto.senhaNova());

        usuario.setSenha(hashSenhaNova);
    }
    
}
