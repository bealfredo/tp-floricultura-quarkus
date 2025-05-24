package br.unitins.topicos1.floricultura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemVenda extends DefaultEntity {
  
  private Double preco;

  private Integer quantidade;

  @ManyToOne
  @JoinColumn(name = "id_planta")
  private Planta planta;

  @ManyToOne
  @JoinColumn(name = "id_venda")
  private Venda venda;

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }

  public Planta getPlanta() {
    return planta;
  }

  public void setPlanta(Planta planta) {
    this.planta = planta;
  }

  public Venda getVenda() {
    return venda;
  }

  public void setVenda(Venda venda) {
    this.venda = venda;
  }

  

}
