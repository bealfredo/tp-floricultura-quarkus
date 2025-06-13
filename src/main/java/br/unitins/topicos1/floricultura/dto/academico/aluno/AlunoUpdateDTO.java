package br.unitins.topicos1.floricultura.dto.academico.aluno;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AlunoUpdateDTO(
    @NotBlank(message = "O campo corRaca não pode ser nulo")
    String corRaca,
    @NotBlank(message = "O campo uf não pode ser nulo")
    String uf,
    @NotBlank(message = "O campo cidade não pode ser nulo")
    String cidade,
    @NotBlank(message = "O campo bairro não pode ser nulo")
    String bairro,
    @NotBlank(message = "O campo cep não pode ser nulo")
    String cep,
    @NotBlank(message = "O campo logradouro não pode ser nulo")
    String logradouro,
    @NotBlank(message = "O campo numero não pode ser nulo")
    String numero,
    @NotBlank(message = "O campo complemento não pode ser nulo")
    String complemento,
    @NotBlank(message = "O campo emailPessoal não pode ser nulo")
    String emailPessoal,
    @NotBlank(message = "O campo telefoneCelular1 não pode ser nulo")
    String telefoneCelular1,
    @NotBlank(message = "O campo telefoneCelular2 não pode ser nulo")
    String telefoneCelular2,
    @NotBlank(message = "O campo telefoneFixo não pode ser nulo")
    String telefoneFixo
) {

}
