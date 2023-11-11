package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO (
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String nome,
    String sobreNome,
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String login,
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String senha,
    LocalDate dataNascimento,
    List<TelefoneDTO> listaTelefone
) {

}
