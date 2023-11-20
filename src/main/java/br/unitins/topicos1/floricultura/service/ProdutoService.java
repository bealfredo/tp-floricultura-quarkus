package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.ProdutoDTO;
import br.unitins.topicos1.floricultura.dto.ProdutoResponseDTO;
import jakarta.validation.Valid;

public interface ProdutoService {

    public ProdutoResponseDTO insert(@Valid ProdutoDTO dto);

    public ProdutoResponseDTO update(ProdutoDTO dto, Long id);

    // public void delete(Long id);

    public List<ProdutoResponseDTO> findAll();    

    public ProdutoResponseDTO findById(Long id);

    public List<ProdutoResponseDTO> findByNome(String nome);

    public ProdutoResponseDTO findByCodigo(String codigo);

    public List<ProdutoResponseDTO> findByTipoProduto(Long id);

    public List<ProdutoResponseDTO> findByFornecedor(Long id);

    public List<ProdutoResponseDTO> findByStatusProduto(Integer id);

}
