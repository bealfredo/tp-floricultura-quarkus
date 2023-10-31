package br.unitins.topicos1.floricultura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class CategoriaProduto extends DefaultEntity {

    @Column (nullable = false)
    private String nome;

    private String descricao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
