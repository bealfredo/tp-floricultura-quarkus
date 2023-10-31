package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.FornecedorDTO;
import br.unitins.topicos1.floricultura.dto.FornecedorResponseDTO;

public interface FornecedorService {
  
  public FornecedorResponseDTO insert(FornecedorDTO dto);

  public FornecedorResponseDTO update(FornecedorDTO dto, Long id);

  public void delete(Long id);

  public List<FornecedorResponseDTO> findAll();

  public FornecedorResponseDTO findById(Long id);

  public List<FornecedorResponseDTO> findByNome(String nome);
  
  public List<FornecedorResponseDTO> findByCnpj(String sigla);
}
