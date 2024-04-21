package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.NivelToxidade;

public record NivelToxicidadeResponseDTO (
  Integer id,
  String label,
  String description
) { 

  public static NivelToxicidadeResponseDTO valueOf(NivelToxidade nivelToxicidade) {
    return new NivelToxicidadeResponseDTO(
      nivelToxicidade.getId(),
      nivelToxicidade.getLabel(),
      nivelToxicidade.getDescription()
    );
  }

}
