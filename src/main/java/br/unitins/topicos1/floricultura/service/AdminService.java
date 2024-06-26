package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.AdminCreateDTO;
import br.unitins.topicos1.floricultura.dto.AdminResponseDTO;
import br.unitins.topicos1.floricultura.dto.AdminSelfUpdateDTO;
import br.unitins.topicos1.floricultura.dto.AdminUpdateDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableDTO;
import jakarta.validation.Valid;

public interface AdminService {

    public AdminResponseDTO insert(@Valid AdminCreateDTO dto);

    public AdminResponseDTO update(@Valid AdminUpdateDTO dto, Long id);

    public AdminResponseDTO selfUpdate(@Valid AdminSelfUpdateDTO dto, Long id);

    public void delete(Long id);

    public AdminResponseDTO findById(Long id);

    public List<AdminResponseDTO> findByAll(int page, int pageSize);

    public Long count();

    public AdminResponseDTO insertExistingUser(EmailAvailableDTO dto);

    // public List<AdminResponseDTO> findByEmail(String nome);

}
