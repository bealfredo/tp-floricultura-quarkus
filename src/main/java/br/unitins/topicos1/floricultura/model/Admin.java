package br.unitins.topicos1.floricultura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Admin extends DefaultEntity {
    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", unique = true)
    private Usuario usuario;

    private TipoAdmin tipoAdmin;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoAdmin getTipoAdmin() {
        return tipoAdmin;
    }

    public void setTipoAdmin(TipoAdmin tipoAdmin) {
        this.tipoAdmin = tipoAdmin;
    }

}
