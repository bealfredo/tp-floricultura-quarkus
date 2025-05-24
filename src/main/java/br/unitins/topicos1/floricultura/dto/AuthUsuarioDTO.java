package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthUsuarioDTO(
  @Email(message = "O campo login deve ser um email válido")
    String login,
    @NotBlank(message = "O campo senha não pode ser nulo")
    String senha,
    @NotNull(message = "O campo idTipoPerfil não pode ser nulo")
    Integer idTipoPerfil
) {
    
}