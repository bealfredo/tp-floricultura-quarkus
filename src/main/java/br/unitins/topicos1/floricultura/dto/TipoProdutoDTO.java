package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TipoProdutoDTO (
  @NotBlank(message = "O campo nome não pode ser nulo")
  String nome,
  String descricao,
  @NotNull(message = "O campo categoriaProduto não pode ser nulo")
  Long categoriaProduto
) {

}
