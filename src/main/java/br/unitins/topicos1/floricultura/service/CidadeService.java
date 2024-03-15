package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.CidadeDTO;
import br.unitins.topicos1.floricultura.dto.CidadeResponseDTO;
import jakarta.validation.Valid;

public interface CidadeService {

    public CidadeResponseDTO insert(@Valid CidadeDTO dto) throws Exception;

    public CidadeResponseDTO update(CidadeDTO dto, Long id) throws Exception;

    public void delete(Long id);

    public CidadeResponseDTO findById(Long id);

    public List<CidadeResponseDTO> findByNome(String nome);

    public List<CidadeResponseDTO> findByAll(); 
}
