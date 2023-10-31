package br.unitins.topicos1.floricultura.service;

import java.util.List;
import br.unitins.topicos1.floricultura.dto.TipoProdutoDTO;
import br.unitins.topicos1.floricultura.dto.TipoProdutoResponseDTO;
import br.unitins.topicos1.floricultura.model.CategoriaProduto;
import br.unitins.topicos1.floricultura.model.TipoProduto;
import br.unitins.topicos1.floricultura.repository.CategoriaProdutoRepository;
import br.unitins.topicos1.floricultura.repository.TipoProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TipoProdutoServiceImpl implements TipoProdutoService {

  @Inject
  TipoProdutoRepository repository;
  @Inject
  CategoriaProdutoRepository repositoryCategoriaProduto;

  @Override
  @Transactional
  public TipoProdutoResponseDTO insert(TipoProdutoDTO dto) throws Exception {
    CategoriaProduto categoriaProduto = repositoryCategoriaProduto.findById(dto.categoriaProduto());

    if (categoriaProduto == null) {
      throw new Exception("A categoria informada não foi encontrada");
    }

    TipoProduto novoTipoProduto = new TipoProduto();

    novoTipoProduto.setNome(dto.nome());
    novoTipoProduto.setDescricao(dto.descricao());
    novoTipoProduto.setCategoriaProduto(categoriaProduto);

    repository.persist(novoTipoProduto);
    
    return TipoProdutoResponseDTO.valueOf(novoTipoProduto);
  }

  @Override
  @Transactional
  public TipoProdutoResponseDTO update(TipoProdutoDTO dto, Long id) throws Exception {
    TipoProduto tipoProduto = repository.findById(id);
    if (tipoProduto == null) {
      throw new NotFoundException();
    }

    CategoriaProduto categoriaProduto = repositoryCategoriaProduto.findById(dto.categoriaProduto());
    if (categoriaProduto == null) {
      throw new Exception("A categoria informada não foi encontrada");
    }

    tipoProduto.setNome(dto.nome());
    tipoProduto.setDescricao(dto.descricao());
    tipoProduto.setCategoriaProduto(categoriaProduto);
   
    return TipoProdutoResponseDTO.valueOf(tipoProduto);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!repository.deleteById(id)) {
      throw new NotFoundException();
    }
  }

  @Override
  public List<TipoProdutoResponseDTO> findAll() {
    return repository.listAll()
      .stream()
      .map(e -> TipoProdutoResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public TipoProdutoResponseDTO findById(Long id) {
    TipoProduto tipoProduto = repository.findById(id);

    if (tipoProduto == null) {
      throw new NotFoundException();
    }

    return TipoProdutoResponseDTO.valueOf(tipoProduto);
  }

  @Override
  public List<TipoProdutoResponseDTO> findByNome(String nome) {
    return repository.findByNome(nome)
      .stream()
      .map(e -> TipoProdutoResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public List<TipoProdutoResponseDTO> findByCategoriaProduto(Long idCategoriaProduto) throws Exception {
    CategoriaProduto categoriaProduto = repositoryCategoriaProduto.findById(idCategoriaProduto);
    if (categoriaProduto == null) {
      throw new Exception("A categoria informada não foi encontrada");
    }

    return repository.findByCategoriaProduto(categoriaProduto)
      .stream()
      .map(e -> TipoProdutoResponseDTO.valueOf(e))
      .toList();
  }

}
