package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.TipoPerfil;

public record TipoPerfilResponseDTO (
  Integer id,
  String label,
  String description
) { 

  public static TipoPerfilResponseDTO valueOf(TipoPerfil tipoPerfil) {
    return new TipoPerfilResponseDTO(
      tipoPerfil.getId(),
      tipoPerfil.getLabel(),
      tipoPerfil.getDescription()
    );
  }

}
