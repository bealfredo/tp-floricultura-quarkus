package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateSenhaDTO (
    @NotBlank(message = "O campo senhaAntiga não pode ser nulo.")
    String senhaAntiga,
    @NotBlank(message = "O campo senhaNova não pode ser nulo.")
    String senhaNova
) {

}
