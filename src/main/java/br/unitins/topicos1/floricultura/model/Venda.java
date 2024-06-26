package br.unitins.topicos1.floricultura.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Venda extends DefaultEntity {

  private LocalDateTime dataHora;

  private String codigo;

  private String chavePix;

  private Double totalVenda;

  @ManyToOne
  @JoinColumn(name = "id_lastStatus")
  private HistoricoStatus lastStatus;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "id_endereco")
  private Endereco endereco;

  @ManyToOne
  @JoinColumn(name = "id_cliente")
  private Cliente cliente;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "venda", fetch = FetchType.LAZY, orphanRemoval = true)
  private List<ItemVenda> itensVenda;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "venda", fetch = FetchType.LAZY, orphanRemoval = true)
  private List<HistoricoStatus> historicoStatus;

  public LocalDateTime getDataHora() {
    return dataHora;
  }

  public void setDataHora(LocalDateTime dataHora) {
    this.dataHora = dataHora;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getChavePix() {
    return chavePix;
  }

  public void setChavePix(String chavePix) {
    this.chavePix = chavePix;
  }

  public Double getTotalVenda() {
    return totalVenda;
  }

  public void setTotalVenda(Double totalPedido) {
    this.totalVenda = totalPedido;
  }

   public HistoricoStatus getLastStatus() {
    return lastStatus;
  }

  public void setLastStatus(HistoricoStatus lastStatus) {
    this.lastStatus = lastStatus;
  }

  public Endereco getEndereco() {
    return endereco;
  }

  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public List<ItemVenda> getItensVenda() {
    return itensVenda;
  }

  public void setItensVenda(List<ItemVenda> itensVenda) {
    this.itensVenda = itensVenda;
  }

  public List<HistoricoStatus> getHistoricoStatus() {
    return historicoStatus;
  }

  public void setHistoricoStatus(List<HistoricoStatus> historicoStatus) {
    this.historicoStatus = historicoStatus;
  }

}
