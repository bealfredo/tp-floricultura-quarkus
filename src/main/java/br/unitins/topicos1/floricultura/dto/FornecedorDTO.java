package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record FornecedorDTO (
  @NotBlank(message = "O campo nome não pode ser nulo")
  String nome,
  @Email(message = "O email não é válido")
  String email,
  @Size(max = 20, message = "O telefone não pode conter mais de 20 dígitos")
  String telefone,
  @Size(max = 20, message = "O cnpj não pode conter mais de 20 dígitos")
  String cnpj
) {

}
