package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioDTO (
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String nome,
    String sobrenome,
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String login,
    @NotBlank(message = "O campo cpf não pode ser nulo.")
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF inválido")
    String cpf,
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String senha,
    LocalDate dataNascimento,
    Long telefone
    // List<EnderecoDTO> listaEndereco
) {

}
