package br.unitins.topicos1.floricultura.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cliente extends DefaultEntity{

  private String carrinho;

  @OneToOne
  @JoinColumn(name = "id_usuario", referencedColumnName = "id", unique = true)
  private Usuario usuario;

  // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "cliente_endereco",
      joinColumns = @JoinColumn(name = "id_cliente"),
      inverseJoinColumns = @JoinColumn(name = "id_endereco")
  )
  private List<Endereco> listaEndereco;

  public String getCarrinho() {
      return carrinho;
  }

  public void setCarrinho(String carrinho) {
      this.carrinho = carrinho;
  }

  public Usuario getUsuario() {
      return usuario;
  }

  public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
  }

  public List<Endereco> getListaEndereco() {
      return listaEndereco;
  }

  public void setListaEndereco(List<Endereco> listaEndereco) {
      this.listaEndereco = listaEndereco;
  }
  
}
