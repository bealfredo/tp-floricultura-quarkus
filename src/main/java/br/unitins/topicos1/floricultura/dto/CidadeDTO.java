package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CidadeDTO {

    @NotBlank(message = "O campo nome não pode ser nulo")
    private final String nome;
    @NotNull(message = "O campo estado não pode ser nulo")
    private final Estado estado;

    public CidadeDTO(String nome, Estado estado) {
        this.nome = nome;
        this.estado = estado;
    }
    
    public String getNome() {
        return nome;
    }

    public Estado getEstado() {
        return estado;
    }
}
