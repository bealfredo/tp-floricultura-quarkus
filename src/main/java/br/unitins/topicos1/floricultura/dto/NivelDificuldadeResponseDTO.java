package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.NivelDificuldade;

public record NivelDificuldadeResponseDTO (
  Integer id,
  String label,
  String description
) { 

  public static NivelDificuldadeResponseDTO valueOf(NivelDificuldade nivelDificuldade) {
    return new NivelDificuldadeResponseDTO(
      nivelDificuldade.getId(),
      nivelDificuldade.getLabel(),
      nivelDificuldade.getDescription()
    );
  }

}
