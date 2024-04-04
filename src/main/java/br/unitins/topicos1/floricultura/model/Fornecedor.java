package br.unitins.topicos1.floricultura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Fornecedor extends DefaultEntity {

    @Column (nullable = false)
    private String nome;

    private String email;

    @Column (length = 20)
    private String telefone;

    @Column (length = 20, unique = true)
    private String cnpj;

    // ! falta implementar o relacionamento com a classe telefone e apagar o atributo telefone

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
