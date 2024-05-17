package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteFastCreateDTO(
    String carrinho,

    @NotBlank(message = "O campo primeiroNome não pode ser nulo")
    String primeiroNome,
    @Email(message = "Email inválido")
    String email,
    @NotBlank(message = "O campo senha não pode ser nulo")
    String senha
) {

}
