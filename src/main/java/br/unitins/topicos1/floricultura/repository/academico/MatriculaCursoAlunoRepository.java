package br.unitins.topicos1.floricultura.repository.academico;

import java.util.List;

import br.unitins.topicos1.floricultura.model.academico.MatriculaCursoAluno;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MatriculaCursoAlunoRepository implements PanacheRepository<MatriculaCursoAluno>{
  // public Aluno findByLogin(String login) {
  //   return find("LOWER(usuario.login) = LOWER(?1)", login.toLowerCase()).firstResult();
  // }

    public List<MatriculaCursoAluno> findByAluno(Long idAluno) {
        return find("aluno.id = ?1", idAluno).list();
    }

}
