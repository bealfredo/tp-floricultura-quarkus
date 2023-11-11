package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.Cidade;
import br.unitins.topicos1.floricultura.model.Estado;

public record CidadeResponseDTO(
    Long id,
    String nome,
    Estado estado
) {
    public static CidadeResponseDTO valueOf(Cidade cidade){
        return new CidadeResponseDTO(
            cidade.getId(), 
            cidade.getNome(),
            cidade.getEstado());
    }
}
