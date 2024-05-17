package br.unitins.topicos1.floricultura.dto;

import java.util.List;

import jakarta.validation.Valid;

public record ClienteDTO(
    String carrinho,
    @Valid
    List<EnderecoDTO> listaEndereco
) {

}
