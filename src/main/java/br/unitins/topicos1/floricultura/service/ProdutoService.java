package br.unitins.topicos1.floricultura.service;

import java.io.File;
import java.util.List;

import br.unitins.topicos1.floricultura.dto.ProdutoDTO;
import br.unitins.topicos1.floricultura.dto.ProdutoResponseDTO;
import br.unitins.topicos1.floricultura.form.ProdutoImageForm;
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

    public ProdutoResponseDTO salvarImagem(ProdutoImageForm form, Long id);

    public File downloadImagem(Long id);

    public void deleteImagem( Long id);

}
