package br.unitins.topicos1.floricultura.model.academico;

import br.unitins.topicos1.floricultura.model.DefaultEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Curso extends DefaultEntity {

    private String nome;
    private String codigo;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Disciplina> disciplinas;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<MatriculaCursoAluno> matriculaCursoAluno;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public List<MatriculaCursoAluno> getMatriculaCursoAluno() {
        return matriculaCursoAluno;
    }

    public void setMatriculaCursoAluno(List<MatriculaCursoAluno> matriculaCursoAluno) {
        this.matriculaCursoAluno = matriculaCursoAluno;
    }
}