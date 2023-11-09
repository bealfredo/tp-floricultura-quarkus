package br.unitins.topicos1.dto;

import br.unitins.topicos1.model.Cidade;
import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO (
    String codigo,
    String rua,
    @NotBlank(message = "O campo cidade não pode ser nulo")
    String bairro,
    @NotBlank(message = "O campo cidade não pode ser nulo")
    String numeroLote,
    String complemento,
    @NotBlank(message = "O campo cidade não pode ser nulo")
    Cidade cidade
) {
    
}
