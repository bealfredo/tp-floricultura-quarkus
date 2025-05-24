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
public class Planta extends DefaultEntity {

    @Column(nullable = false)
    private String nomeComum;

    private String nomeCientifico;

    @Column(length = 400)
    private String descricao;

    private String codigo;

    private String imagemPrincipal;

    private String[] imagens;

    @Column(nullable = false)
    private Double precoVenda;

    @Column(nullable = false)
    private Double precoCusto;

    @Column(nullable = false)
    private Double desconto;

    @Column(nullable = false)
    private Integer quantidadeDisponivel;

    @Column(nullable = false)
    private Integer quantidadeVendido;

    private LocalDateTime dataDisponivel;

    private String origem;

    private String tempoCrescimento;

    // @Column(nullable = false)
    private StatusPlanta statusPlanta;

    // @Column(nullable = false)
    private NivelDificuldade nivelDificuldade;

    // @Column(nullable = false)
    private NivelToxidade nivelToxidade;

    // @Column(nullable = false)
    private PortePlanta portePlanta;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "planta_tag", joinColumns = @JoinColumn(name = "id_planta"), inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private List<Tag> tags;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "id_categoriabiologica")
    private CategoriaPlanta categoriaBiologica;

    public String getNomeComum() {
        return nomeComum;
    }

    public void setNomeComum(String nomeComum) {
        this.nomeComum = nomeComum;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
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

    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    public String[] getImagens() {
        return imagens;
    }

    public void setImagens(String[] imagens) {
        this.imagens = imagens;
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

    public Integer getQuantidadeVendido() {
        return quantidadeVendido;
    }

    public void setQuantidadeVendido(Integer quantidadeVendido) {
        this.quantidadeVendido = quantidadeVendido;
    }

    public LocalDateTime getDataDisponivel() {
        return dataDisponivel;
    }

    public void setDataDisponivel(LocalDateTime dataDisponivel) {
        this.dataDisponivel = dataDisponivel;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getTempoCrescimento() {
        return tempoCrescimento;
    }

    public void setTempoCrescimento(String tempoCrescimento) {
        this.tempoCrescimento = tempoCrescimento;
    }

    public StatusPlanta getStatusPlanta() {
        return statusPlanta;
    }

    public void setStatusPlanta(StatusPlanta statusPlanta) {
        this.statusPlanta = statusPlanta;
    }

    public NivelDificuldade getNivelDificuldade() {
        return nivelDificuldade;
    }

    public void setNivelDificuldade(NivelDificuldade nivelDificuldade) {
        this.nivelDificuldade = nivelDificuldade;
    }

    public NivelToxidade getNivelToxidade() {
        return nivelToxidade;
    }

    public void setNivelToxidade(NivelToxidade nivelToxidade) {
        this.nivelToxidade = nivelToxidade;
    }

    public PortePlanta getPortePlanta() {
        return portePlanta;
    }

    public void setPortePlanta(PortePlanta portePlanta) {
        this.portePlanta = portePlanta;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public CategoriaPlanta getCategoriaBiologica() {
        return categoriaBiologica;
    }

    public void setCategoriaBiologica(CategoriaPlanta categoriaPlanta) {
        this.categoriaBiologica = categoriaPlanta;
    }


    
}
