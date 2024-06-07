package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record PlantasDoCarrinhoDTO (
    ItemVendaDTO[] carrinho
) {

}
