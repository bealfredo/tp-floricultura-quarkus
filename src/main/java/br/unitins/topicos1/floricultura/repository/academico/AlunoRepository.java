package br.unitins.topicos1.floricultura.repository.academico;

import br.unitins.topicos1.floricultura.model.academico.Aluno;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AlunoRepository implements PanacheRepository<Aluno>{
  public Aluno findByLogin(String login) {
    return find("LOWER(usuario.login) = LOWER(?1)", login.toLowerCase()).firstResult();
  }

}
