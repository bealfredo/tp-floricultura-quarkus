package br.unitins.topicos1.floricultura.model.academico;

import br.unitins.topicos1.floricultura.model.DefaultEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class MatriculaCursoAluno extends DefaultEntity {

    private String turma;
    private String modalidade;
    private String tipoDeIngresso;
    private String matriz;

    @ManyToOne
    private Curso curso;

    @ManyToOne
    private Aluno aluno;

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getTipoDeIngresso() {
        return tipoDeIngresso;
    }

    public void setTipoDeIngresso(String tipoDeIngresso) {
        this.tipoDeIngresso = tipoDeIngresso;
    }

    public String getMatriz() {
        return matriz;
    }

    public void setMatriz(String matriz) {
        this.matriz = matriz;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
