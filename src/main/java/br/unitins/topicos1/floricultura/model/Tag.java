package br.unitins.topicos1.floricultura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tag extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @Column(length = 400)
    private String descricao;

    private Integer prioridade;

    private Boolean ativa;

    @ManyToOne
    @JoinColumn(name = "id_categoriaplanta")
    private CategoriaPlanta categoriaPlanta;

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

    public Integer getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Integer prioridade) {
        this.prioridade = prioridade;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public CategoriaPlanta getCategoriaPlanta() {
        return categoriaPlanta;
    }

    public void setCategoriaPlanta(CategoriaPlanta categoriaPlanta) {
        this.categoriaPlanta = categoriaPlanta;
    }

}
