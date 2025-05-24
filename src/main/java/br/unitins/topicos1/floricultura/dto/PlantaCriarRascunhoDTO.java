package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record PlantaCriarRascunhoDTO (
    @NotBlank(message = "O campo nome comum deve ser informado")
    String nomeComum,
    @NotNull(message = "O campo fornecedor não pode ser nulo")
    Long idFornecedor,
    @NotNull(message = "O campo categoria biologica da planta não pode ser nulo")
    Long idCategoriaBiologica
) {

}
