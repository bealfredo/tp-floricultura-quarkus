package br.unitins.topicos1.floricultura.service;

import java.util.List;
import br.unitins.topicos1.floricultura.dto.CategoriaProdutoDTO;
import br.unitins.topicos1.floricultura.dto.CategoriaProdutoResponseDTO;
import br.unitins.topicos1.floricultura.model.CategoriaProduto;
import br.unitins.topicos1.floricultura.repository.CategoriaProdutoRepository;
import br.unitins.topicos1.floricultura.repository.TipoProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CategoriaProdutoServiceServiceImpl implements CategoriaProdutoService {

  @Inject
  CategoriaProdutoRepository repository;
  @Inject
  TipoProdutoRepository repositoryTipoProduto;

  @Override
  @Transactional
  public CategoriaProdutoResponseDTO insert(CategoriaProdutoDTO dto) {
    CategoriaProduto novaCategoriaProduto = new CategoriaProduto();

    novaCategoriaProduto.setNome(dto.nome());
    novaCategoriaProduto.setDescricao(dto.descricao());

    repository.persist(novaCategoriaProduto);
    
    return CategoriaProdutoResponseDTO.valueOf(novaCategoriaProduto);
  }

  @Override
  @Transactional
  public CategoriaProdutoResponseDTO update(CategoriaProdutoDTO dto, Long id) {
    CategoriaProduto categoriaProduto = repository.findById(id);
    if (categoriaProduto != null) {
      categoriaProduto.setNome(dto.nome());
      categoriaProduto.setDescricao(dto.descricao());
    } else {
      throw new NotFoundException();
    }

    return CategoriaProdutoResponseDTO.valueOf(categoriaProduto);
  }

  @Override
  @Transactional
  public void delete(Long id) throws Exception {
    CategoriaProduto categoriaProduto = repository.findById(id);
    if (categoriaProduto == null) {
      throw new NotFoundException();
    }

    if (repositoryTipoProduto.findByCategoriaProduto(categoriaProduto).size() > 0) {
      throw new Exception("Não é possivel apagar a categoria porquê há tiposdeproduto associados a ela");
    }

    if (!repository.deleteById(id)) {
      throw new NotFoundException();
    }
  }

  @Override
  public List<CategoriaProdutoResponseDTO> findAll() {
    return repository.listAll()
      .stream()
      .map(e -> CategoriaProdutoResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public CategoriaProdutoResponseDTO findById(Long id) {
    CategoriaProduto categoriaProduto = repository.findById(id);

    if (categoriaProduto == null) {
      throw new NotFoundException();
    }

    return CategoriaProdutoResponseDTO.valueOf(categoriaProduto);
  }

  @Override
  public List<CategoriaProdutoResponseDTO> findByNome(String nome) {
    return repository.findByNome(nome)
      .stream()
      .map(e -> CategoriaProdutoResponseDTO.valueOf(e))
      .toList();
  }
}
