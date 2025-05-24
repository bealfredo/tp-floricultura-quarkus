package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.TipoAdmin;

public record TipoAdminResponseDTO (
  Integer id,
  String label,
  String description
) { 

  public static TipoAdminResponseDTO valueOf(TipoAdmin tipoAdmin) {
    return new TipoAdminResponseDTO(
      tipoAdmin.getId(),
      tipoAdmin.getLabel(),
      tipoAdmin.getDescription()
    );
  }

}
