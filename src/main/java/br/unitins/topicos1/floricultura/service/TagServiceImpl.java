package br.unitins.topicos1.floricultura.service;

import java.util.List;
import br.unitins.topicos1.floricultura.dto.TagDTO;
import br.unitins.topicos1.floricultura.dto.TagResponseDTO;
import br.unitins.topicos1.floricultura.model.CategoriaPlanta;
import br.unitins.topicos1.floricultura.model.Tag;
import br.unitins.topicos1.floricultura.repository.CategoriaPlantaRepository;
import br.unitins.topicos1.floricultura.repository.TagRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TagServiceImpl implements TagService {

  @Inject
  TagRepository repository;
  @Inject
  CategoriaPlantaRepository repositoryCategoriaPlanta;

  @Override
  @Transactional
  public TagResponseDTO insert(TagDTO dto) {
    Tag novaTag = new Tag();

    CategoriaPlanta categoriaPlanta = repositoryCategoriaPlanta.findById(dto.idCategoriaPlanta());
    if (categoriaPlanta == null) {
      throw new ValidationException("categoriaPlanta", "Id para categoria planta inválido.");
    }

    if (repository.findByNomeECategoria(dto.nome(), true, categoriaPlanta).count() > 0) {
      throw new ValidationException("nome", "Nome já cadastrado na categoria informada.");
    }

    novaTag.setNome(dto.nome());
    novaTag.setDescricao(dto.descricao());
    novaTag.setPrioridade(dto.prioridade());
    novaTag.setAtiva(dto.ativa());
    novaTag.setCategoriaPlanta(categoriaPlanta);

    repository.persist(novaTag);
    
    return TagResponseDTO.valueOf(novaTag);
  }

  @Override
  @Transactional
  public TagResponseDTO update(TagDTO dto, Long id) {
    Tag tag = repository.findById(id);

    if (tag == null) {
      throw new NotFoundException();
    }

    CategoriaPlanta categoriaPlanta = repositoryCategoriaPlanta.findById(dto.idCategoriaPlanta());
    if (categoriaPlanta == null) {
      throw new ValidationException("categoriaPlanta", "Id para categoria planta inválido.");
    }

    if (!tag.getNome().equals(dto.nome()) && repository.findByNomeECategoria(dto.nome(), true, categoriaPlanta).count() > 0) {
      throw new ValidationException("nome", "Nome já cadastrado na categoria informada.");
    }

    tag.setNome(dto.nome());
    tag.setDescricao(dto.descricao());
    tag.setPrioridade(dto.prioridade());
    tag.setAtiva(dto.ativa());
    tag.setCategoriaPlanta(categoriaPlanta);

    return TagResponseDTO.valueOf(tag);
  }

  @Override
  @Transactional
  public void delete(Long id) throws Exception {
    Tag tag = repository.findById(id);
    if (tag == null) {
      throw new NotFoundException();
    }

    // ! do with produto
    // if (repositoryTipoProduto.findByCategoriaPlanta(categoriaPlanta).size() > 0) {
    //   throw new Exception("Não é possivel apagar a categoria porquê há tiposdeproduto associados a ela");
    // }

    if (!repository.deleteById(id)) {
      throw new NotFoundException();
    }
  }

  @Override
  public List<TagResponseDTO> findAll() {
    return repository.listAll()
      .stream()
      .map(e -> TagResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public TagResponseDTO findById(Long id) {
    Tag tag = repository.findById(id);

    if (tag == null) {
      throw new NotFoundException();
    }

    return TagResponseDTO.valueOf(tag);
  }

  @Override
  public List<TagResponseDTO> findByNome(String nome) {
    return repository.findByNome(nome, false)
      .stream()
      .map(e -> TagResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public List<TagResponseDTO> findByAtiva(Boolean idAtiva) {
    return repository.findByAtiva(idAtiva)
      .stream()
      .map(e -> TagResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public List<TagResponseDTO> findByCategoriaPlanta(Long idCategoriaPlanta) {
    CategoriaPlanta categoriaPlanta = repositoryCategoriaPlanta.findById(idCategoriaPlanta);

    if (categoriaPlanta == null) {
      throw new NotFoundException("Id para tipo categoria inválido.");
    }

    return repository.findByCategoriaPlanta(categoriaPlanta)
      .stream()
      .map(e -> TagResponseDTO.valueOf(e))
      .toList();
  }
}
