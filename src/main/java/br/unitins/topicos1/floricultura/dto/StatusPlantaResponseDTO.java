package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.StatusPlanta;

public record StatusPlantaResponseDTO (
  Integer id,
  String label,
  String description
) { 

  public static StatusPlantaResponseDTO valueOf(StatusPlanta statusPlanta) {
    return new StatusPlantaResponseDTO(
      statusPlanta.getId(),
      statusPlanta.getLabel(),
      statusPlanta.getDescription()
    );
  }

}
