package br.unitins.topicos1.floricultura.service;

import br.unitins.topicos1.floricultura.dto.AuthUsuarioDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableResponseDTO;

public interface UsuarioService {
    // public List<UsuarioResponseDTO> findAll();

    // public UsuarioResponseDTO findById(Long id);
    
    // public UsuarioResponseDTO findByLoginAndSenha(String login, String senha);
  
    // public void updateSenha(UsuarioUpdateSenhaDTO dto);

    public EmailAvailableResponseDTO checkEmailAvailable(EmailAvailableDTO dto);

    public String login(AuthUsuarioDTO dto);
    
}
