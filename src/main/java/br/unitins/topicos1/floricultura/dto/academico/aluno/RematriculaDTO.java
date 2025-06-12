package br.unitins.topicos1.floricultura.dto.academico.aluno;

import java.util.List;

import jakarta.validation.constraints.Size;

public record RematriculaDTO(
    // @NotBlank(message = "O campo nome não pode ser nulo")
    // String nome,
    // @NotBlank(message = "O campo sobrenome não pode ser nulo")
    // String sobrenome,
    // @NotBlank(message = "O campo cpf não pode ser nulo")
    // @Pattern(regexp = "^[0-9]{11}$", message = "O campo cpf deve conter exatamente 11 números")
    // String cpf,
    // @NotNull(message = "O campo dataNascimento não pode ser nulo")
    // LocalDate dataNascimento,

    // @NotBlank(message = "O campo matrícula não pode ser nulo")
    // String matricula


    @Size(min = 1)
    List<Long> disciplinasId
) {

}
