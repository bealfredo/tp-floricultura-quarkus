package br.unitins.topicos1.floricultura.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Produto extends DefaultEntity {

    @Column (nullable = false)
    private String nome;

    private String descricao;

    private String codigo;

    private StatusProduto statusProduto;

    private String imagem;
    
    private Double precoVenda;

    private double precoCusto;
    
    private Double desconto;
    
    private Integer quantidadeDisponivel;
    
    private LocalDateTime dataDisponivel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "produto_tipoproduto",
        joinColumns = @JoinColumn(name = "id_produto"),
        inverseJoinColumns = @JoinColumn(name = "id_tipoproduto")
    )
    private List<TipoProduto> tipoProduto;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public StatusProduto getStatusProduto() {
        return statusProduto;
    }

    public void setStatusProduto(StatusProduto statusProduto) {
        this.statusProduto = statusProduto;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public LocalDateTime getDataDisponivel() {
        return dataDisponivel;
    }

    public void setDataDisponivel(LocalDateTime dataDisponivel) {
        this.dataDisponivel = dataDisponivel;
    }

    public List<TipoProduto> getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(List<TipoProduto> tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }


}
