package br.unitins.topicos1.floricultura.service;

import br.unitins.topicos1.floricultura.dto.UsuarioResponseDTO;

public interface JwtService {

    public String generateJwt(UsuarioResponseDTO dto);
    
}
