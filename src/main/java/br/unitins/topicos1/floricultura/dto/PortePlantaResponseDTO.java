package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.PortePlanta;

public record PortePlantaResponseDTO (
  Integer id,
  String label,
  String description
) { 

  public static PortePlantaResponseDTO valueOf(PortePlanta portePlanta) {
    return new PortePlantaResponseDTO(
      portePlanta.getId(),
      portePlanta.getLabel(),
      portePlanta.getDescription()
    );
  }

}
