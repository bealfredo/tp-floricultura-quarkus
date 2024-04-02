package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.CategoriaPlantaDTO;
import br.unitins.topicos1.floricultura.dto.CategoriaPlantaResponseDTO;
import br.unitins.topicos1.floricultura.model.CategoriaPlanta;
import br.unitins.topicos1.floricultura.model.TipoCategoria;
import br.unitins.topicos1.floricultura.repository.CategoriaPlantaRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CategoriaPlantaServiceImpl implements CategoriaPlantaService {

  @Inject
  CategoriaPlantaRepository repository;
  // @Inject
  // TipoProdutoRepository repositoryTipoProduto;

  @Override
  @Transactional
  public CategoriaPlantaResponseDTO insert(CategoriaPlantaDTO dto) {
    CategoriaPlanta novaCategoriaPlanta = new CategoriaPlanta();

    TipoCategoria tipoCategoria = TipoCategoria.valueOf(dto.idTipoCategoria());
    if (tipoCategoria == null) {
      throw new ValidationException("tipoCategoria", "Id para tipo categoria inválido.");
    }

    if (repository.findByNome(dto.nome(), true).count() > 0) {
      throw new ValidationException("nome", "Nome já cadastrado.");
    }

    novaCategoriaPlanta.setNome(dto.nome());
    novaCategoriaPlanta.setDescricao(dto.descricao());
    novaCategoriaPlanta.setPrioridade(dto.prioridade());
    novaCategoriaPlanta.setAtiva(dto.ativa());
    novaCategoriaPlanta.setTipoCategoria(tipoCategoria);


    repository.persist(novaCategoriaPlanta);
    
    return CategoriaPlantaResponseDTO.valueOf(novaCategoriaPlanta);
  }

  @Override
  @Transactional
  public CategoriaPlantaResponseDTO update(CategoriaPlantaDTO dto, Long id) {
    CategoriaPlanta categoriaPlanta = repository.findById(id);

    if (categoriaPlanta == null) {
      throw new NotFoundException();
    }

    TipoCategoria tipoCategoria = TipoCategoria.valueOf(dto.idTipoCategoria());
    if (tipoCategoria == null) {
      throw new ValidationException("tipoCategoria", "Id para tipo categoria inválido.");
    }

    if (!categoriaPlanta.getNome().equals(dto.nome()) && repository.findByNome(dto.nome(), true).count() > 0) {
      throw new ValidationException("nome", "Nome já cadastrado.");
    }

    // if (repository.findByNome(dto.nome(), true).count() > 0) {
    //   throw new ValidationException("nome", "Nome já cadastrado.");
    // }

    categoriaPlanta.setNome(dto.nome());
    categoriaPlanta.setDescricao(dto.descricao());
    categoriaPlanta.setPrioridade(dto.prioridade());
    categoriaPlanta.setAtiva(dto.ativa());
    categoriaPlanta.setTipoCategoria(tipoCategoria);

    return CategoriaPlantaResponseDTO.valueOf(categoriaPlanta);
  }

  @Override
  @Transactional
  public void delete(Long id) throws Exception {
    CategoriaPlanta categoriaPlanta = repository.findById(id);
    if (categoriaPlanta == null) {
      throw new NotFoundException();
    }

    // if (repositoryTipoProduto.findByCategoriaPlanta(categoriaPlanta).size() > 0) {
    //   throw new Exception("Não é possivel apagar a categoria porquê há tiposdeproduto associados a ela");
    // }

    if (!repository.deleteById(id)) {
      throw new NotFoundException();
    }
  }

  @Override
  public List<CategoriaPlantaResponseDTO> findAll() {
    return repository.listAll()
      .stream()
      .map(e -> CategoriaPlantaResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public CategoriaPlantaResponseDTO findById(Long id) {
    CategoriaPlanta categoriaPlanta = repository.findById(id);

    if (categoriaPlanta == null) {
      throw new NotFoundException();
    }

    return CategoriaPlantaResponseDTO.valueOf(categoriaPlanta);
  }

  @Override
  public List<CategoriaPlantaResponseDTO> findByNome(String nome) {
    return repository.findByNome(nome, false)
      .stream()
      .map(e -> CategoriaPlantaResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public List<CategoriaPlantaResponseDTO> findByAtiva(Boolean idAtiva) {
    return repository.findByAtiva(idAtiva)
      .stream()
      .map(e -> CategoriaPlantaResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public List<CategoriaPlantaResponseDTO> findByTipoCategoria(Integer idTipoCategoria) {
    
    TipoCategoria tipoCategoria = TipoCategoria.valueOf(idTipoCategoria);
    if (tipoCategoria == null) {
      throw new NotFoundException("Id para tipo categoria inválido.");
    }

    return repository.findByTipoCategoria(tipoCategoria)
      .stream()
      .map(e -> CategoriaPlantaResponseDTO.valueOf(e))
      .toList();
  }
}
