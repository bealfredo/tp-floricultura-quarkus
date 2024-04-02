package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoriaPlantaDTO (
  @NotBlank(message = "O campo nome não pode ser nulo")
  String nome,
  String descricao,
  @NotNull(message = "O campo prioridade não pode ser nulo")
  Integer prioridade,
  @NotNull(message = "O campo ativa não pode ser nulo")
  Boolean ativa,
  @NotNull(message = "O campo idTipoCategoria não pode ser nulo")
  Integer idTipoCategoria
) {

}
