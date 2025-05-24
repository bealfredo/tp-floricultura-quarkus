package br.unitins.topicos1.floricultura.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class HistoricoStatus extends DefaultEntity {
  
  private LocalDateTime data;

  private StatusVenda statusVenda;

  @ManyToOne
  @JoinColumn(name = "id_venda")
  private Venda venda;

  public LocalDateTime getData() {
    return data;
  }

  public void setData(LocalDateTime data) {
    this.data = data;
  }

  public StatusVenda getStatusVenda() {
    return statusVenda;
  }

  public void setStatusVenda(StatusVenda statusVenda) {
    this.statusVenda = statusVenda;
  }

  public Venda getVenda() {
    return venda;
  }

  public void setVenda(Venda venda) {
    this.venda = venda;
  }
}
