package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.ClienteDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import jakarta.validation.Valid;

public interface ClienteService {

    public ClienteResponseDTO insert(@Valid ClienteDTO dto) throws Exception;

    public ClienteResponseDTO update(ClienteDTO dto, Long id) throws Exception;

    public void delete(Long id);

    public ClienteResponseDTO findById(Long id);
    
    public List<ClienteResponseDTO> findByAll(); 
}