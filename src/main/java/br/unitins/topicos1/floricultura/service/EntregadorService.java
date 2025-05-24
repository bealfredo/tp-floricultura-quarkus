package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.EntregadorCreateDTO;
import br.unitins.topicos1.floricultura.dto.EntregadorResponseDTO;
import br.unitins.topicos1.floricultura.dto.EntregadorUpdateDTO;
import jakarta.validation.Valid;

public interface EntregadorService {

    public EntregadorResponseDTO insert(@Valid EntregadorCreateDTO dto);

    public EntregadorResponseDTO update(@Valid EntregadorUpdateDTO dto, Long id);

    public void delete(Long id);

    public EntregadorResponseDTO findById(Long id);

    public List<EntregadorResponseDTO> findByAll(int page, int pageSize); 

}
