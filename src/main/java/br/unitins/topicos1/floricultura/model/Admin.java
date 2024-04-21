package br.unitins.topicos1.floricultura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Admin extends DefaultEntity {

    private TipoAdmin tipoAdmin;

    @OneToOne
    private Usuario usuario;

    public TipoAdmin getTipoAdmin() {
        return tipoAdmin;
    }

    public void setTipoAdmin(TipoAdmin tipoAdmin) {
        this.tipoAdmin = tipoAdmin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
