package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotNull;

public record PlantaUpdateStatusPlantaDTO (
    @NotNull(message = "O campo idStatus não pode ser nulo")
    Integer idStatus
) {

}
