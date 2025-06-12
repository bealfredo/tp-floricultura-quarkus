package br.unitins.topicos1.floricultura.dto.academico.aluno;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AlunoFastCreateDTO(
    @NotBlank(message = "O campo matrícula não pode ser nulo")
    String matricula,

    @NotBlank(message = "O campo primeiroNome não pode ser nulo")
    String primeiroNome,

    @NotBlank(message = "O campo sobrenome não pode ser nulo")
    String sobrenome,

    @Email(message = "Email inválido")
    String email,

    @NotBlank(message = "O campo cpf não pode ser nulo")
    @Pattern(regexp = "^[0-9]{11}$", message = "O campo cpf deve conter exatamente 11 números")
    String cpf,

    @NotBlank(message = "O campo senha não pode ser nulo")
    String senha,

    LocalDate dataNascimento
) {

}
