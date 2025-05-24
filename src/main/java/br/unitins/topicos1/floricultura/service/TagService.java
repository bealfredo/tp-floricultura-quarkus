package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.CategoriaPlantaUpdateAtivaDTO;
import br.unitins.topicos1.floricultura.dto.TagDTO;
import br.unitins.topicos1.floricultura.dto.TagResponseDTO;

public interface TagService {
  
  public TagResponseDTO insert(TagDTO dto);

  public TagResponseDTO update(TagDTO dto, Long id);

  public void updateAtiva(CategoriaPlantaUpdateAtivaDTO dto, Long id);

  public void delete(Long id) throws Exception;

  public List<TagResponseDTO> findAll();

  public TagResponseDTO findById(Long id);

  public List<TagResponseDTO> findByNome(String nome);

  public List<TagResponseDTO> findByAtiva(Boolean idAtiva);

  public List<TagResponseDTO> findByCategoriaPlanta(Long idCategoriaPlanta);

}
