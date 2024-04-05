package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.EntregadorDTO;
import br.unitins.topicos1.floricultura.dto.EntregadorResponseDTO;
import br.unitins.topicos1.floricultura.model.Entregador;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.repository.EntregadorRepository;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EntregadorServiceImpl implements EntregadorService{
    
    @Inject
    EntregadorRepository repository;
    @Inject
    UsuarioRepository repositoryUsuario;

    @Override
    @Transactional
    public EntregadorResponseDTO insert(EntregadorDTO dto) throws Exception {
        
        Usuario usuario = repositoryUsuario.findById(dto.usuario());

        if (usuario == null) {
            throw new Exception("O usuario informado não foi encontrada");
        }

        Entregador novoEntregador = new Entregador();
        novoEntregador.setCnh(dto.cnh());
        novoEntregador.setCnpj(dto.cnpj());
        novoEntregador.setUsuario(usuario);

        repository.persist(novoEntregador);

        return EntregadorResponseDTO.valueOf(novoEntregador);
    }

    @Override
    @Transactional
    public EntregadorResponseDTO update(EntregadorDTO dto, Long id) throws Exception {

        Usuario usuario = repositoryUsuario.findById(dto.usuario());

        if (usuario == null) {
            throw new Exception("O usuario informado não foi encontrada");
        }
      
        Entregador entregador = repository.findById(id);
        if (entregador != null) {
            entregador.setCnh(dto.cnh());
            entregador.setCnpj(dto.cnpj());
            entregador.setUsuario(usuario);
        } else {
            throw new NotFoundException();
        }
        return EntregadorResponseDTO.valueOf(entregador);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) 
            throw new NotFoundException();
    }

    @Override
    public EntregadorResponseDTO findById(Long id) {
        return EntregadorResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<EntregadorResponseDTO> findByAll() {
        return repository.listAll().stream()
            .map(e -> EntregadorResponseDTO.valueOf(e)).toList();
    }
}
