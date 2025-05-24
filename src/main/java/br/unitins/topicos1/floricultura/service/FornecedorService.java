package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.FornecedorDTO;
import br.unitins.topicos1.floricultura.dto.FornecedorResponseDTO;
import jakarta.validation.Valid;

public interface FornecedorService {
  
  public FornecedorResponseDTO insert(@Valid FornecedorDTO dto);

  public FornecedorResponseDTO update(@Valid FornecedorDTO dto, Long id);

  public void delete(Long id);

  public List<FornecedorResponseDTO> findAll();

  public FornecedorResponseDTO findById(Long id);

  public List<FornecedorResponseDTO> findByNome(String nome);
  
  public List<FornecedorResponseDTO> findByCnpj(String sigla);

  public List<FornecedorResponseDTO> findByEmail(String email);
}
