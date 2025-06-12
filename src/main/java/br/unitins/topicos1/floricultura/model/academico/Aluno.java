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
}
