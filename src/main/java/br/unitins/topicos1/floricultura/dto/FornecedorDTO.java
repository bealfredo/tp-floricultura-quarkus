package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record FornecedorDTO (
  @NotBlank(message = "O campo nome não pode ser nulo")
  String nome,
  @NotBlank(message = "O campo email não pode ser nulo")
  @Email(message = "O email não é válido")
  String email,
  @Valid
  TelefoneDTO telefone,
  @Pattern(regexp = "^$|\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos ou ser vazio")
  String cnpj
) {

}