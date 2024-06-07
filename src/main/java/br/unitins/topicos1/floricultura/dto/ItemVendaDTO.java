package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ItemVendaDTO (
  @Min(1)
  Integer quantidade,
  @NotBlank(message = "O campo planta não pode ser nullo")
  Long planta
) {

}
