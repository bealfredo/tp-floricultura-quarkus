package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TelefoneDTO (
    @NotBlank(message = "O ddd não pode ser nulo")
    @Pattern(regexp = "\\d{2}", message = "O DDD deve conter exatamente 2 dígitos numéricos")
    String ddd,
    @NotBlank(message = "O numero não pode ser nulo")
    @Pattern(regexp = "\\d{9}", message = "O número deve conter exatamente 9 dígitos numéricos")
    String numero
) {
    
}