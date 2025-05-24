package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.CidadeDTO;
import br.unitins.topicos1.floricultura.dto.CidadeResponseDTO;
import jakarta.validation.Valid;

public interface CidadeService {

    public CidadeResponseDTO insert(@Valid CidadeDTO dto);

    public CidadeResponseDTO update(@Valid CidadeDTO dto, Long id);

    public void delete(Long id);

    public CidadeResponseDTO findById(Long id);

    public List<CidadeResponseDTO> findByNome(String nome);

    public List<CidadeResponseDTO> findByEstado(Long idEstado);

    public List<CidadeResponseDTO> findByAll(); 
}
