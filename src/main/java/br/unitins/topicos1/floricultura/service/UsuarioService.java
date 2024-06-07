package br.unitins.topicos1.floricultura.service;

import br.unitins.topicos1.floricultura.dto.AdminResponseDTO;
import br.unitins.topicos1.floricultura.dto.AuthUsuarioDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableResponseDTO;
import br.unitins.topicos1.floricultura.dto.EmailTakenClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.EntregadorResponseDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioTiposPerfilByEmailResponseDTO;

public interface UsuarioService {
    // public List<UsuarioResponseDTO> findAll();

    // public UsuarioResponseDTO findById(Long id);
    
    // public UsuarioResponseDTO findByLoginAndSenha(String login, String senha);
  
    // public void updateSenha(UsuarioUpdateSenhaDTO dto);

    public EmailAvailableResponseDTO checkEmailAvailable(EmailAvailableDTO dto);

    public EmailTakenClienteResponseDTO checkEmailTakenCliente(EmailAvailableDTO dto);

    public UsuarioTiposPerfilByEmailResponseDTO usuarioTiposPerfilByEmail(EmailAvailableDTO dto);

    public String login(AuthUsuarioDTO dto);

    public AdminResponseDTO userInfoAdmin();
    public ClienteResponseDTO userInfoCliente();
    public EntregadorResponseDTO userInfoEntregador();
    
}
