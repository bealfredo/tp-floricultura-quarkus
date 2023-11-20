package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.StatusProduto;

public record StatusProdutoResponseDTO (
  Integer id,
  String label
) { 

  public static StatusProdutoResponseDTO valueOf(StatusProduto statusProduto) {
    return new StatusProdutoResponseDTO(
      statusProduto.getId(),
      statusProduto.getLabel()
    );
  }

}
