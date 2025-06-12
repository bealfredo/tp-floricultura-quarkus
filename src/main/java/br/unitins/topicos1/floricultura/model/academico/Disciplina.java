package br.unitins.topicos1.floricultura.model.academico;

import java.util.List;

import br.unitins.topicos1.floricultura.model.DefaultEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Disciplina extends DefaultEntity {

    private String periodoLetivo;
    private String codigo;
    private String nome;
    private Integer faltasPermitidas;
    private Double cargaHoraria;
    private Integer creditos;
    private Integer periodoCurso;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @OneToMany(mappedBy = "disciplina") // Relaciona com o atributo 'disciplina' em MatriculaDisciplinaAluno
    private List<MatriculaDisciplinaAluno> matriculasDisciplinaAluno;


    public String getPeriodoLetivo() {
        return periodoLetivo;
    }

    public void setPeriodoLetivo(String periodoLetivo) {
        this.periodoLetivo = periodoLetivo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getFaltasPermitidas() {
        return faltasPermitidas;
    }

    public void setFaltasPermitidas(Integer faltasPermitidas) {
        this.faltasPermitidas = faltasPermitidas;
    }

    public Double getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Double cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Integer getPeriodoCurso() {
        return periodoCurso;
    }

    public void setPeriodoCurso(Integer periodoCurso) {
        this.periodoCurso = periodoCurso;
    }

    public List<MatriculaDisciplinaAluno> getMatriculasDisciplinaAluno() {
        return matriculasDisciplinaAluno;
    }

    public void setMatriculasDisciplinaAluno(List<MatriculaDisciplinaAluno> matriculasDisciplinaAluno) {
        this.matriculasDisciplinaAluno = matriculasDisciplinaAluno;
    }
}
