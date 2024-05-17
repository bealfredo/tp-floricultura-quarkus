package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteUpdateDTO(
    String carrinho,
    @Valid
    List<EnderecoDTO> listaEndereco,

    @NotBlank(message = "O campo nome não pode ser nulo")
    String nome,
    @NotBlank(message = "O campo sobrenome não pode ser nulo")
    String sobrenome,
    @NotBlank(message = "O campo cpf não pode ser nulo")
    String cpf,
    @NotNull(message = "O campo dataNascimento não pode ser nulo")
    LocalDate dataNascimento,
    @Valid
    TelefoneDTO telefone
) {

}
