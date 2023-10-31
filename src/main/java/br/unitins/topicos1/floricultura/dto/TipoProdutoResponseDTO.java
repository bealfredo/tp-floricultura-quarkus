package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.CategoriaProduto;
import br.unitins.topicos1.floricultura.model.TipoProduto;

public record TipoProdutoResponseDTO (
  Long id,
  String nome,
  String descricao,
  CategoriaProduto categoriaProduto
) { 

  public static TipoProdutoResponseDTO valueOf(TipoProduto tipoProduto) {
    return new TipoProdutoResponseDTO(
      tipoProduto.getId(),
      tipoProduto.getNome(),
      tipoProduto.getDescricao(),
      tipoProduto.getCategoriaProduto()
    );
  }

}
