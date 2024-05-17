package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.ClienteDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import jakarta.validation.Valid;

public interface ClienteService {

    public ClienteResponseDTO insert(@Valid ClienteDTO dto);

    public ClienteResponseDTO update(@Valid ClienteDTO dto, Long id);

    public void delete(Long id);

    public ClienteResponseDTO findById(Long id);

    public List<ClienteResponseDTO> findByAll(int page, int pageSize); 

}
