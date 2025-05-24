package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.Telefone;

public record TelefoneResponseDTO(
    Long id,
    String ddd,
    String numero
) {
    public static TelefoneResponseDTO valueOf(Telefone telefone) {
        return new TelefoneResponseDTO(
            telefone.getId(),
            telefone.getDdd(),
            telefone.getNumero());
    }
}
