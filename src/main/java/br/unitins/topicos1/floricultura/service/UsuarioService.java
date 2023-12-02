package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.UsuarioDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioResponseDTO;
import jakarta.validation.Valid;

public interface UsuarioService {

    public UsuarioResponseDTO insert(@Valid UsuarioDTO dto);

    public UsuarioResponseDTO update(UsuarioDTO dto, Long id);

    public void delete(Long id);

    public List<UsuarioResponseDTO> findAll();

    public UsuarioResponseDTO findById(Long id);
  
    public List<UsuarioResponseDTO> findByNome(String nome);
  
    public UsuarioResponseDTO findByLoginAndSenha(String login, String senha);
  
    public UsuarioResponseDTO findByLogin(String login); 
}
