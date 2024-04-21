package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.ClienteDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import br.unitins.topicos1.floricultura.model.Cliente;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.repository.ClienteRepository;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService{
    
    @Inject
    ClienteRepository repository;
    @Inject
    UsuarioRepository repositoryUsuario;

    @Override
    @Transactional
    public ClienteResponseDTO insert(@Valid ClienteDTO dto) throws Exception {
        
        Usuario usuario = repositoryUsuario.findById(dto.usuario());

        if (usuario == null) {
            throw new Exception("O usuario informado não foi encontrada");
        }
        Cliente novoCliente = new Cliente();
        novoCliente.setUsuario(usuario);

        repository.persist(novoCliente);

        return ClienteResponseDTO.valueOf(novoCliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(ClienteDTO dto, Long id) throws Exception {

        Usuario usuario = repositoryUsuario.findById(dto.usuario());

        if (usuario == null) {
            throw new Exception("O usuario informado não foi encontrada");
        }
      
        Cliente cliente = repository.findById(id);
        if (cliente != null) {
            cliente.setUsuario(usuario);
        } else {
            throw new NotFoundException();
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) 
            throw new NotFoundException();
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        return ClienteResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<ClienteResponseDTO> findByAll() {
        return repository.listAll().stream()
            .map(e -> ClienteResponseDTO.valueOf(e)).toList();
    }
}
