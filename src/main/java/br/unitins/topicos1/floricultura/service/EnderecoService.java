package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.EnderecoDTO;
import br.unitins.topicos1.floricultura.dto.EnderecoResponseDTO;

public interface EnderecoService {
  
  public EnderecoResponseDTO insert(EnderecoDTO dto) throws Exception;

  public EnderecoResponseDTO update(EnderecoDTO dto, Long id) throws Exception;

  public void delete(Long id);

  public List<EnderecoResponseDTO> findAll();

  public EnderecoResponseDTO findById(Long id);

  public List<EnderecoResponseDTO> findByNome(String nome);
  
  public List<EnderecoResponseDTO> findByCep(String cep);

}
