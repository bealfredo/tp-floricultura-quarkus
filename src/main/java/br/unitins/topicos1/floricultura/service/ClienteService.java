package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.ClienteUpdateDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableResponseDTO;
import br.unitins.topicos1.floricultura.dto.ClienteFastCreateDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import jakarta.validation.Valid;

public interface ClienteService {

    public EmailAvailableResponseDTO checkEmailAvailable(@Valid EmailAvailableDTO dto);

    public ClienteResponseDTO insert(@Valid ClienteFastCreateDTO dto);

    public ClienteResponseDTO update(@Valid ClienteUpdateDTO dto, Long id);

    public void delete(Long id);

    public ClienteResponseDTO findById(Long id);

    public List<ClienteResponseDTO> findByAll(int page, int pageSize); 

}
