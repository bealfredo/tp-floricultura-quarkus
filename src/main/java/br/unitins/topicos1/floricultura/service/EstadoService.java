package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.EstadoDTO;
import br.unitins.topicos1.floricultura.dto.EstadoResponseDTO;

public interface EstadoService {
  
  public EstadoResponseDTO insert(EstadoDTO dto);

  public EstadoResponseDTO update(EstadoDTO dto, Long id);

  public void delete(Long id);

  public List<EstadoResponseDTO> findAll();

  public EstadoResponseDTO findById(Long id);

  public List<EstadoResponseDTO> findByNome(String nome);
  
  public List<EstadoResponseDTO> findBySigla(String sigla);
  
  public List<EstadoResponseDTO> findByNomeESigla(String txt);

}
