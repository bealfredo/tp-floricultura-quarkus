package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.Entregador;
import br.unitins.topicos1.floricultura.model.Usuario;

public record EntregadorResponseDTO(
    String cnpj,
    String cnh,
    Usuario usuario
) {
    public static EntregadorResponseDTO valueOf(Entregador entregador) {
        return new EntregadorResponseDTO(
            entregador.getCnpj(),
            entregador.getCnh(),
            entregador.getUsuario());
    }
}
