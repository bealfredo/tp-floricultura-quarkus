package br.unitins.topicos1.floricultura.service;

import br.unitins.topicos1.floricultura.dto.UsuarioResponseDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioUpdateSenhaDTO;

public interface UsuarioLogadoService {

    public UsuarioResponseDTO getUsuario();

    public void updateSenha(UsuarioUpdateSenhaDTO dto);
    
}
