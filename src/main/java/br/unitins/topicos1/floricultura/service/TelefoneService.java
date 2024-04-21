package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.TelefoneDTO;
import br.unitins.topicos1.floricultura.dto.TelefoneResponseDTO;

public interface TelefoneService {
  public TelefoneResponseDTO insert(TelefoneDTO dto) throws Exception;

  public TelefoneResponseDTO update(TelefoneDTO dto, Long id) throws Exception;

  public void delete(Long id) ;

  public List<TelefoneResponseDTO> findAll();

  public TelefoneResponseDTO findById(Long id);
}
