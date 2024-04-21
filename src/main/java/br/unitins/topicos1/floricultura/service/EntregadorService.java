package br.unitins.topicos1.floricultura.service;
import java.util.List;

import br.unitins.topicos1.floricultura.dto.EntregadorDTO;
import br.unitins.topicos1.floricultura.dto.EntregadorResponseDTO;

public interface EntregadorService {
  
  public EntregadorResponseDTO insert(EntregadorDTO dto) throws Exception;

  public EntregadorResponseDTO update(EntregadorDTO dto, Long id) throws Exception;

  public void delete(Long id);

  public EntregadorResponseDTO findById(Long id);

  public List<EntregadorResponseDTO> findByAll(); 
}
