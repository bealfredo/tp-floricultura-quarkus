package br.unitins.topicos1.floricultura.repository.academico;

import br.unitins.topicos1.floricultura.model.academico.Disciplina;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DisciplinaRepository implements PanacheRepository<Disciplina>{
  public Disciplina findByLogin(String login) {
    return find("LOWER(usuario.login) = LOWER(?1)", login.toLowerCase()).firstResult();
  }

}
