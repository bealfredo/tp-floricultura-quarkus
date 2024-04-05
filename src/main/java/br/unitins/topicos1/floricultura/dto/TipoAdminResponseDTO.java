package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.TipoAdmin;

public record TipoAdminResponseDTO(
    Integer id,
    String label
) {
    public static TipoAdminResponseDTO valueOf(TipoAdmin tipoAdmin) {
        return new TipoAdminResponseDTO(
          tipoAdmin.getId(),
          tipoAdmin.getLabel()
        );
    }
}
