package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.Estado;

public record EstadoResponseDTO(
  Long id,
  String nome,
  String sigla
) { 

  public static EstadoResponseDTO valueOf(Estado estado) {
    return new EstadoResponseDTO(
      estado.getId(),
      estado.getNome(),
      estado.getSigla()
    );
  }

}
