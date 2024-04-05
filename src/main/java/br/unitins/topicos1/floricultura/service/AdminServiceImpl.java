package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.AdminDTO;
import br.unitins.topicos1.floricultura.dto.AdminResponseDTO;
import br.unitins.topicos1.floricultura.model.Admin;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.repository.AdminRepository;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class AdminServiceImpl implements AdminService{
    
    @Inject
    AdminRepository repository;
    @Inject
    UsuarioRepository repositoryUsuario;

    @Override
    @Transactional
    public AdminResponseDTO insert(AdminDTO dto) throws Exception {
    
        Usuario usuario = repositoryUsuario.findById(dto.usuario());
        if (usuario == null) {
            throw new Exception("O usuario informado não foi encontrada");
        }
        Admin novoAdmin = new Admin();
        novoAdmin.setUsuario(usuario);
        repository.persist(novoAdmin);

        return AdminResponseDTO.valueOf(novoAdmin);
    }

    @Override
    @Transactional
    public AdminResponseDTO update(AdminDTO dto, Long id) throws Exception {

        Usuario usuario = repositoryUsuario.findById(dto.usuario());

        if (usuario == null) {
            throw new Exception("O usuario informado não foi encontrada");
        }
      
        Admin cliente = repository.findById(id);
        if (cliente != null) {
            cliente.setUsuario(usuario);
        } else {
            throw new NotFoundException();
        }
        return AdminResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) 
            throw new NotFoundException();
    }

    @Override
    public AdminResponseDTO findById(Long id) {
        return AdminResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<AdminResponseDTO> findByAll() {
        return repository.listAll().stream()
            .map(e -> AdminResponseDTO.valueOf(e)).toList();
    }
}
