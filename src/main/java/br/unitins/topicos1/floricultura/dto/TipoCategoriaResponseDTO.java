package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.TipoCategoria;

public record TipoCategoriaResponseDTO (
  Integer id,
  String label,
  String description
) { 

  public static TipoCategoriaResponseDTO valueOf(TipoCategoria tipoCategoria) {
    return new TipoCategoriaResponseDTO(
      tipoCategoria.getId(),
      tipoCategoria.getLabel(),
      tipoCategoria.getDescription()
    );
  }

}
