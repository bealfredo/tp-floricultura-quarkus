package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteExistingUserDTO(
  @Email(message = "O campo login deve ser um email válido")
  String email,
  @NotBlank(message = "O campo senha não pode ser nulo")
  String passwordExisting
) {
    
}