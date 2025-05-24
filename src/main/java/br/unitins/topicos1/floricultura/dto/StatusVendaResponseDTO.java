package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.StatusVenda;

public record StatusVendaResponseDTO (
  Integer id,
  String label
) { 

  public static StatusVendaResponseDTO valueOf(StatusVenda statusVenda) {
    return new StatusVendaResponseDTO(
      statusVenda.getId(),
      statusVenda.getLabel()
    );
  }

}
