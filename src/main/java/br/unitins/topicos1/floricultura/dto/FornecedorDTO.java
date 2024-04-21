package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record FornecedorDTO (
  @NotBlank(message = "O campo nome não pode ser nulo")
  String nome,
  @Email(message = "O email não é válido")
  String email,
  @Pattern(regexp = "^$|\\d{11}", message = "O telefone deve conter exatamente 11 dígitos ou ser vazio")
  String telefone,
  @Pattern(regexp = "^$|\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos ou ser vazio")
  String cnpj
) {

}