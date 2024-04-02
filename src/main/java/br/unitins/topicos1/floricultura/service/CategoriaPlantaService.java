package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.CategoriaPlantaDTO;
import br.unitins.topicos1.floricultura.dto.CategoriaPlantaResponseDTO;

public interface CategoriaPlantaService {
  
  public CategoriaPlantaResponseDTO insert(CategoriaPlantaDTO dto);

  public CategoriaPlantaResponseDTO update(CategoriaPlantaDTO dto, Long id);

  public void delete(Long id) throws Exception;

  public List<CategoriaPlantaResponseDTO> findAll();

  public CategoriaPlantaResponseDTO findById(Long id);

  public List<CategoriaPlantaResponseDTO> findByNome(String nome);

  public List<CategoriaPlantaResponseDTO> findByAtiva(Boolean idAtiva);

  public List<CategoriaPlantaResponseDTO> findByTipoCategoria(Integer idTipoCategoria);

}
