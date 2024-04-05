package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.AdminDTO;
import br.unitins.topicos1.floricultura.dto.AdminResponseDTO;

public interface AdminService {

    public AdminResponseDTO insert(AdminDTO dto) throws Exception;

    public AdminResponseDTO update(AdminDTO dto, Long id) throws Exception;

    public void delete(Long id);

    public AdminResponseDTO findById(Long id);

    public List<AdminResponseDTO> findByAll(); 
}