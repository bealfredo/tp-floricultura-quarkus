package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ClienteUpdateDTO(
    @NotNull(message = "A lista de endereços não pode ser nula")
    @Valid
    List<EnderecoDTO> listaEndereco,

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
    TelefoneDTO telefone
) {

}
