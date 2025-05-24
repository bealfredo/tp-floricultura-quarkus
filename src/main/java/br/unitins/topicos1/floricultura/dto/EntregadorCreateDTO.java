package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EntregadorCreateDTO(
    @Email(message = "Email inválido")
    String email,
    @NotBlank(message = "O campo senha não pode ser nulo")
    String senha,

    @NotBlank(message = "O campo nome não pode ser nulo")
    String nome,
    @NotBlank(message = "O campo sobrenome não pode ser nulo")
    String sobrenome,
    @NotBlank(message = "O campo cpf não pode ser nulo")
    @Pattern(regexp = "^[0-9]{11}$", message = "O campo cpf deve conter exatamente 11 números")
    String cpf,
    @NotNull(message = "O campo dataNascimento não pode ser nulo")
    LocalDate dataNascimento,
    @Valid
    TelefoneDTO telefone,

    @Pattern(regexp = "^[0-9]{11}$|^$", message = "O campo CNH deve conter exatamente 11 números ou estar vazio")
    String cnh,
    @Pattern(regexp = "^[0-9]{14}$|^$", message = "O campo CNPJ deve conter exatamente 14 números ou estar vazio")
    String cnpj
) {

}
