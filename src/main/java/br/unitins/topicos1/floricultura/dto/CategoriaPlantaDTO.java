package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoriaPlantaDTO (
  @NotBlank(message = "O campo nome não pode ser nulo")
  String nome,
  @Size(max = 400, message = "O campo descricao deve ter no máximo 400 caracteres")
  String descricao,
  @NotNull(message = "O campo prioridade não pode ser nulo")
  Integer prioridade,
  @NotNull(message = "O campo ativa não pode ser nulo")
  Boolean ativa,
  @NotNull(message = "O campo idTipoCategoria não pode ser nulo")
  Integer idTipoCategoria
) {

}
