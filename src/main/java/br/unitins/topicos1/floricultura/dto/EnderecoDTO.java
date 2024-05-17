package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnderecoDTO (
    @NotBlank(message = "O campo nome não pode ser nulo")
    String nome,
    @NotBlank(message = "O campo cep não pode ser nulo")
    @Pattern(regexp = "^[0-9]{5}[0-9]{3}$", message = "O campo CEP deve conter 8 dígitos numéricos")
    String cep,
    @NotBlank(message = "O campo rua não pode ser nulo")
    String rua,
    @NotBlank(message = "O campo bairro não pode ser nulo")
    String bairro,
    @NotBlank(message = "O campo numeroLote não pode ser nulo")
    String numeroLote,
    String complemento,
    @NotNull(message = "O campo cidade não pode ser nulo")
    Long cidade
) {
    
}
