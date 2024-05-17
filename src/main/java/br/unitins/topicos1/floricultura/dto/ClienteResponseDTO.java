package br.unitins.topicos1.floricultura.dto;

import java.util.List;

import br.unitins.topicos1.floricultura.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    String carrinho,
    List<EnderecoResponseDTO> listaEndereco
) {
    public static ClienteResponseDTO valueOf(Cliente cliente){
        return new ClienteResponseDTO(
            cliente.getId(), 
            cliente.getCarrinho(),
            EnderecoResponseDTO.valueOf(cliente.getListaEndereco())
        );
    }
}
