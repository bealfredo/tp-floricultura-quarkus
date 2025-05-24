package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EstadoDTO(
    @NotBlank(message = "O campo nome não pode ser nulo")
    String nome,
    @Pattern(regexp = "^[A-Za-z]{2}$", message = "A sigla deve conter exatamente 2 letras")
    String sigla
) {
}