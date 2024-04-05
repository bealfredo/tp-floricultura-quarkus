package br.unitins.topicos1.floricultura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Cliente extends DefaultEntity {
    
    String carrinho;

    @OneToOne
    private Usuario usuario;

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

    
}
