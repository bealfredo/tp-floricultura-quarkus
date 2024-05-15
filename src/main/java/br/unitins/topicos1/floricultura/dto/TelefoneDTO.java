package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotBlank;

public record TelefoneDTO (
    @NotBlank(message = "O ddd não pode ser nulo")
    String ddd,
    @NotBlank(message = "O numero não pode ser nulo")
    String numero
) {
    
}