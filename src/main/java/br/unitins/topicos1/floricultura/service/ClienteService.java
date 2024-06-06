package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.ClienteFastCreateDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.ClienteUpdateDTO;
import jakarta.validation.Valid;

public interface ClienteService {

    public ClienteResponseDTO insert(@Valid ClienteFastCreateDTO dto);

    public ClienteResponseDTO update(@Valid ClienteUpdateDTO dto, Long id);

    public void delete(Long id);

    public ClienteResponseDTO findById(Long id);

    public List<ClienteResponseDTO> findByAll(int page, int pageSize); 

    public Long count();

}
