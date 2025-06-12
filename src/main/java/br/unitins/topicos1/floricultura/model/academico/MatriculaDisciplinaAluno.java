package br.unitins.topicos1.floricultura.model.academico;

import br.unitins.topicos1.floricultura.model.DefaultEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class MatriculaDisciplinaAluno extends DefaultEntity {

    private Double a1;
    private Double a2;
    private Double exameFinal;
    private Double frequencia;
    private Double mediaFinal;

    private String statusMatriculaDisciplina;

    @ManyToOne
    private Disciplina disciplina;

    @ManyToOne
    private Aluno aluno;

    public Double getA1() {
        return a1;
    }

    public void setA1(Double a1) {
        this.a1 = a1;
    }

    public Double getA2() {
        return a2;
    }

    public void setA2(Double a2) {
        this.a2 = a2;
    }

    public Double getExameFinal() {
        return exameFinal;
    }

    public void setExameFinal(Double exameFinal) {
        this.exameFinal = exameFinal;
    }

    public Double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Double frequencia) {
        this.frequencia = frequencia;
    }

    public Double getMediaFinal() {
        return mediaFinal;
    }

    public void setMediaFinal(Double mediaFinal) {
        this.mediaFinal = mediaFinal;
    }

    public String getStatusMatriculaDisciplina() {
        return statusMatriculaDisciplina;
    }

    public void setStatusMatriculaDisciplina(String statusMatriculaDisciplina) {
        this.statusMatriculaDisciplina = statusMatriculaDisciplina;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    // public enum StatusMatriculaDisciplina {
    //     APROVADO,
    //     REPROVADO,
    //     MATRICULADO,
    //     TRANCADO,
    //     PENDENTE
    // }
}
