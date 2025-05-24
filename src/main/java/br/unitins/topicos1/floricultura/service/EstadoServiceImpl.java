package br.unitins.topicos1.floricultura.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.unitins.topicos1.floricultura.dto.EstadoDTO;
import br.unitins.topicos1.floricultura.dto.EstadoResponseDTO;
import br.unitins.topicos1.floricultura.model.Estado;
import br.unitins.topicos1.floricultura.repository.EstadoRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

  @Inject
  EstadoRepository repository;

  private void valid(EstadoDTO dto, Estado obj2Update) {
    if (obj2Update != null) {
      if (!dto.nome().equals(obj2Update.getNome())) {
        List<Estado> estados = repository.findByNome(dto.nome(), false);
        if (!estados.isEmpty()) {
          throw new ValidationException("nome", "Nome já cadastrado.");
        }
      }

      if (!dto.sigla().toUpperCase().equals(obj2Update.getSigla().toUpperCase())) {
        List<Estado> estados = repository.findBySigla(dto.sigla().toUpperCase(), false);
        if (!estados.isEmpty()) {
          throw new ValidationException("sigla", "Sigla já cadastrada.");
        }
      }
    } else {
      List<Estado> estados = repository.findByNome(dto.nome(), false);
      if (!estados.isEmpty()) {
        throw new ValidationException("nome", "Nome já cadastrado.");
      }

      estados = repository.findBySigla(dto.sigla().toUpperCase(), false);
      if (!estados.isEmpty()) {
        throw new ValidationException("sigla", "Sigla já cadastrada.");
      }
    }

  }

  @Override
  @Transactional
  public EstadoResponseDTO insert(EstadoDTO dto) {

    valid(dto, null);

    Estado novoEstado = new Estado();
    novoEstado.setNome(dto.nome());
    novoEstado.setSigla(dto.sigla().toUpperCase());

    repository.persist(novoEstado);
    
    return EstadoResponseDTO.valueOf(novoEstado);
  }

  @Override
  @Transactional
  public EstadoResponseDTO update(@Valid EstadoDTO dto, Long id) {
    Estado estado = repository.findById(id);
    if (estado == null) {
      throw new NotFoundException();
    }

    valid(dto, estado);

    estado.setNome(dto.nome());
    estado.setSigla(dto.sigla().toUpperCase());

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
    return repository.findByNome(nome, true)
      .stream()
      .map(e -> EstadoResponseDTO.valueOf(e))
      .toList();
  }
  
  @Override
  public List<EstadoResponseDTO> findBySigla(String sigla) {
    return repository.findBySigla(sigla, true)
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
