package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotNull;

public record PlantaUpdateAddRemoveQuantidadeDTO (
    @NotNull(message = "O campo quantidade não pode ser nulo")
    Integer quantidade
) {

}
