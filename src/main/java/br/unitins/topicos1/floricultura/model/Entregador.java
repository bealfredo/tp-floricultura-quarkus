package br.unitins.topicos1.floricultura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Entregador extends DefaultEntity {

    private String cnpj;
    private String cnh;

    @OneToOne
    private Usuario usuario;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    

}
