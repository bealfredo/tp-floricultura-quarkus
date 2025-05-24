package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CidadeDTO(
    @NotBlank(message = "O campo nome não pode ser nulo") 
    String nome,
    @NotNull(message = "O campo estado não pode ser nulo") 
    Long estado,
    @NotNull(message = "O campo frete não pode ser nulo")
    @Min(value = 0, message = "O campo frete não pode ser menor que 0")
    Double frete
) {

}
