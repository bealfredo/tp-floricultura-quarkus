package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.CategoriaProdutoDTO;
import br.unitins.topicos1.floricultura.dto.CategoriaProdutoResponseDTO;

public interface CategoriaProdutoService {
  
  public CategoriaProdutoResponseDTO insert(CategoriaProdutoDTO dto);

  public CategoriaProdutoResponseDTO update(CategoriaProdutoDTO dto, Long id);

  public void delete(Long id) throws Exception;

  public List<CategoriaProdutoResponseDTO> findAll();

  public CategoriaProdutoResponseDTO findById(Long id);

  public List<CategoriaProdutoResponseDTO> findByNome(String nome);
  
}
