package br.unitins.topicos1.floricultura.repository.academico;

import java.util.List;

import br.unitins.topicos1.floricultura.model.academico.MatriculaDisciplinaAluno;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MatriculaDisciplinaAlunoRepository implements PanacheRepository<MatriculaDisciplinaAluno>{
  // public Aluno findByLogin(String login) {
  //   return find("LOWER(usuario.login) = LOWER(?1)", login.toLowerCase()).firstResult();
  // }

    public List<MatriculaDisciplinaAluno> findByAluno(Long idAluno) {
        return find("aluno.id = ?1", idAluno).list();
    }

}
