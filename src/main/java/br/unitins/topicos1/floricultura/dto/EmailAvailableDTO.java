package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.Email;

public record EmailAvailableDTO(
    @Email(message = "Email inválido") 
    String email
) {

}
