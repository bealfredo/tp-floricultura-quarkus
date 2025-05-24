package br.unitins.topicos1.floricultura.service;

import br.unitins.topicos1.floricultura.model.TipoPerfil;
import br.unitins.topicos1.floricultura.model.Usuario;

public interface JwtService {

    public String generateJwt(Usuario dto, TipoPerfil tipoPerfil);

    public String getTokenForTest();
    
}
