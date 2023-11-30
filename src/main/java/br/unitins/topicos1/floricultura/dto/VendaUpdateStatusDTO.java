package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record VendaUpdateStatusDTO (
  @Min(1)
  @Max(6)
  Integer novoStatusId
) {

}
