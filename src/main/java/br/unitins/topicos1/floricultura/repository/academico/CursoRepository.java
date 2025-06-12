package br.unitins.topicos1.floricultura.repository.academico;

import java.util.List;

import br.unitins.topicos1.floricultura.model.academico.Curso;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CursoRepository implements PanacheRepository<Curso>{
  // public Aluno findByLogin(String login) {
  //   return find("LOWER(usuario.login) = LOWER(?1)", login.toLowerCase()).firstResult();
  // }

    public List<Curso> findByAluno(Long idAluno) {
        return find("SELECT c FROM Curso c JOIN c.matriculaCursoAluno m WHERE m.aluno.id = ?1", idAluno).list();
    }

    public List<Curso> findByAlunoWithMatriculaCurso(Long idAluno) {
        return find("SELECT c FROM Curso c JOIN c.matriculaCursoAluno m WHERE m.aluno.id = ?1", idAluno).list();
    }

}
