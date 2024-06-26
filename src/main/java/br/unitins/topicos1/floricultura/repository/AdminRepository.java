package br.unitins.topicos1.floricultura.repository;

import br.unitins.topicos1.floricultura.model.Admin;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdminRepository implements PanacheRepository<Admin>{
  public Admin findByLogin(String login) {
    return find("LOWER(usuario.login) = LOWER(?1)", login.toLowerCase()).firstResult();
  }

}
