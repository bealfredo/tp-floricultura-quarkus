package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.Cliente;
import br.unitins.topicos1.floricultura.model.Usuario;

public record ClienteResponseDTO(
    Long id,
    String carrinho,
    Usuario usuario
) {
    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getCarrinho(),
            cliente.getUsuario());
    }
}
