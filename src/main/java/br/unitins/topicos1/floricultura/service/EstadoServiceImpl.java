package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.EstadoDTO;
import br.unitins.topicos1.floricultura.dto.EstadoResponseDTO;
import br.unitins.topicos1.floricultura.model.Estado;
import br.unitins.topicos1.floricultura.repository.EstadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped //só vai assistir um no servidor, então vai montar só uma vez e não um para cada usuário
public class EstadoServiceImpl implements EstadoService {

  @Inject
  EstadoRepository repository;

  @Override
  @Transactional
  public EstadoResponseDTO insert(EstadoDTO dto) {
    Estado novoEstado = new Estado();
    novoEstado.setNome(dto.getNome());
    novoEstado.setSigla(dto.getSigla());

    repository.persist(novoEstado);
    
    return EstadoResponseDTO.valueOf(novoEstado);
  }

  @Override
  @Transactional
  public EstadoResponseDTO update(EstadoDTO dto, Long id) {
    Estado estado = repository.findById(id);
    if (estado != null) {
      estado.setNome(dto.getNome());
      estado.setSigla(dto.getSigla());
    } else {
      throw new NotFoundException();
    }

    return EstadoResponseDTO.valueOf(estado);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!repository.deleteById(id)) {
      throw new NotFoundException();
    }
  }

  @Override
  public List<EstadoResponseDTO> findAll() {
    return repository.listAll()
      .stream()
      .map(e -> EstadoResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public EstadoResponseDTO findById(Long id) {
    return EstadoResponseDTO.valueOf(repository.findById(id));
  }

  @Override
  public List<EstadoResponseDTO> findByNome(String nome) {
    return repository.findByNome(nome)
      .stream()
      .map(e -> EstadoResponseDTO.valueOf(e))
      .toList();
  }
  
}
