package br.unitins.topicos1.floricultura.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Produto extends DefaultEntity {

    @Column (nullable = false)
    private String nome;

    private String descricao;

    private String codigo;
    
    private String imagem;
    
    private Double precoVenda;

    private double precoCusto;
    
    private Double desconto;
    
    private Integer quantidadeDisponivel;
    
    private LocalDateTime dataDisponivel;

    @OneToMany(mappedBy = "tipoProduto")
    private List<TipoProduto> tipoProduto;

    

    @ManyToOne
    @JoinColumn(name = "id_categoriaproduto")
    private CategoriaProduto categoriaProduto;

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

    public CategoriaProduto getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

}
