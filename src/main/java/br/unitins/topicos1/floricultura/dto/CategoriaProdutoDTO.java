package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaProdutoDTO (
  @NotBlank(message = "O campo nome não pode ser nulo")
  String nome,
  String descricao
) {

}
