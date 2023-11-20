package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.topicos1.floricultura.model.Produto;
import br.unitins.topicos1.floricultura.model.StatusProduto;

public record ProdutoResponseDTO(
    Long id,
    String nome,
    String descricao,
    String codigo,
    StatusProduto statusProduto,
    String imagem,
    Double precoVenda,
    double precoCusto,
    Double desconto,
    Integer quantidadeDisponivel,
    LocalDateTime dataDisponivel,
    FornecedorResponseDTO fornecedor,
    List<TipoProdutoResponseDTO> tipoProduto) {

  public static ProdutoResponseDTO valueOf(Produto produto) {
    return new ProdutoResponseDTO(
        produto.getId(),
        produto.getNome(),
        produto.getDescricao(),
        produto.getCodigo(),
        produto.getStatusProduto(),
        produto.getImagem(),
        produto.getPrecoVenda(),
        produto.getPrecoCusto(),
        produto.getDesconto(),
        produto.getQuantidadeDisponivel(),
        produto.getDataDisponivel(),
        FornecedorResponseDTO.valueOf(produto.getFornecedor()),
        TipoProdutoResponseDTO.valueOf(produto.getTipoProduto())                                                                          
    );
  }
}