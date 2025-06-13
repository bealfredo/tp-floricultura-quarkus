package br.unitins.topicos1.floricultura.model.academico;

import java.util.List;

import br.unitins.topicos1.floricultura.model.DefaultEntity;
import br.unitins.topicos1.floricultura.model.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Aluno extends DefaultEntity {

    private String matricula;
    private Integer periodoAtual;
    private Boolean matriculaPendente;
    private String[] imagens;
    private String imagemPrincipal;


    // Informações pessoais
    private String corRaca;
    
    // Endereço
    private String uf;
    
    private String cidade;
    
    private String bairro;
    
    private String cep;
    
    private String logradouro;
    
    private String numero;
    
    private String complemento;
    
    // Contatos
    private String emailPessoal;
    
    private String telefoneCelular1;
    
    private String telefoneCelular2;
    
    private String telefoneFixo;


    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", unique = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MatriculaCursoAluno> matriculasCursoAluno;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MatriculaDisciplinaAluno> matriculasDisciplinaAluno;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<MatriculaCursoAluno> getMatriculasCursoAluno() {
        return matriculasCursoAluno;
    }

    public void setMatriculasCursoAluno(List<MatriculaCursoAluno> matriculasCursoAluno) {
        this.matriculasCursoAluno = matriculasCursoAluno;
    }

    public List<MatriculaDisciplinaAluno> getMatriculasDisciplinaAluno() {
        return matriculasDisciplinaAluno;
    }

    public void setMatriculasDisciplinaAluno(List<MatriculaDisciplinaAluno> matriculasDisciplinaAluno) {
        this.matriculasDisciplinaAluno = matriculasDisciplinaAluno;
    }

    public Integer getPeriodoAtual() {
        return periodoAtual;
    }

    public void setPeriodoAtual(Integer periodoAtual) {
        this.periodoAtual = periodoAtual;
    }

    public Boolean getMatriculaPendente() {
        return matriculaPendente;
    }

    public void setMatriculaPendente(Boolean matriculaPendente) {
        this.matriculaPendente = matriculaPendente;
    }

    public String[] getImagens() {
        return imagens;
    }

    public void setImagens(String[] imagens) {
        this.imagens = imagens;
    }

    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }



    public String getCorRaca() {
        return corRaca;
    }
    public void setCorRaca(String corRaca) {
        this.corRaca = corRaca;
    }

    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEmailPessoal() {
        return emailPessoal;
    }
    public void setEmailPessoal(String emailPessoal) {
        this.emailPessoal = emailPessoal;
    }

    public String getTelefoneCelular1() {
        return telefoneCelular1;
    }
    public void setTelefoneCelular1(String telefoneCelular1) {
        this.telefoneCelular1 = telefoneCelular1;
    }

    public String getTelefoneCelular2() {
        return telefoneCelular2;
    }
    public void setTelefoneCelular2(String telefoneCelular2) {
        this.telefoneCelular2 = telefoneCelular2;
    }

    public String getTelefoneFixo() {
        return telefoneFixo;
    }
    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }
}
