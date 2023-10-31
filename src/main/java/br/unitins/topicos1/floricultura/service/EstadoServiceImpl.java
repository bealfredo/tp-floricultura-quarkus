package br.unitins.topicos1.floricultura.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.unitins.topicos1.floricultura.dto.EstadoDTO;
import br.unitins.topicos1.floricultura.dto.EstadoResponseDTO;
import br.unitins.topicos1.floricultura.model.Estado;
import br.unitins.topicos1.floricultura.repository.EstadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

  @Inject
  EstadoRepository repository;

  @Override
  @Transactional
  public EstadoResponseDTO insert(EstadoDTO dto) {
    Estado novoEstado = new Estado();
    novoEstado.setNome(dto.getNome());
    novoEstado.setSigla(dto.getSigla().toUpperCase());

    repository.persist(novoEstado);
    
    return EstadoResponseDTO.valueOf(novoEstado);
  }

  @Override
  @Transactional
  public EstadoResponseDTO update(EstadoDTO dto, Long id) {
    Estado estado = repository.findById(id);
    if (estado != null) {
      estado.setNome(dto.getNome());
      estado.setSigla(dto.getSigla().toUpperCase());
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
    Estado estado = repository.findById(id);

    if (estado == null) {
      throw new NotFoundException();
    }

    return EstadoResponseDTO.valueOf(estado);
  }

  @Override
  public List<EstadoResponseDTO> findByNome(String nome) {
    return repository.findByNome(nome)
      .stream()
      .map(e -> EstadoResponseDTO.valueOf(e))
      .toList();
  }
  
  @Override
  public List<EstadoResponseDTO> findBySigla(String sigla) {
    return repository.findBySigla(sigla)
      .stream()
      .map(e -> EstadoResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public List<EstadoResponseDTO> findByNomeESigla(String txt) {
    List<EstadoResponseDTO> foundByNome = findByNome(txt);
    List<EstadoResponseDTO> foundBySigla = findBySigla(txt);  

    Set<Long> idsExistentes = new HashSet<>();
    List<EstadoResponseDTO> all = new ArrayList<>();
    
    for (EstadoResponseDTO estado : foundByNome) {
      if (idsExistentes.add(estado.id())) {
        all.add(estado);
      }
    }

    for (EstadoResponseDTO estado : foundBySigla) {
      if (idsExistentes.add(estado.id())) {
        all.add(estado);
      }
    }

    return all;
  }
}
