package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.CategoriaProduto;

public record CategoriaProdutoResponseDTO (
  Long id,
  String nome,
  String descricao
) { 

  public static CategoriaProdutoResponseDTO valueOf(CategoriaProduto categoriaProduto) {
    return new CategoriaProdutoResponseDTO(
      categoriaProduto.getId(),
      categoriaProduto.getNome(),
      categoriaProduto.getDescricao()
    );
  }

}
