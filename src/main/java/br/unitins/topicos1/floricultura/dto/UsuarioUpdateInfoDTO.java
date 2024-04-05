package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.topicos1.floricultura.model.Telefone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioUpdateInfoDTO (
    @NotBlank(message = "O campo nome não pode ser nulo.")
    String nome,
    String sobreNome,
    @NotBlank(message = "O campo cpf não pode ser nulo.")
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF inválido")
    String cpf,
    LocalDate dataNascimento,
    Telefone telefone,
    List<EnderecoDTO> listaEndereco
) {

}
