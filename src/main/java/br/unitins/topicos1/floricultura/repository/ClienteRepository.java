package br.unitins.topicos1.floricultura.repository;

import br.unitins.topicos1.floricultura.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente>{
  public Cliente findByLogin(String login) {
    return find("LOWER(usuario.login) = LOWER(?1)", login.toLowerCase()).firstResult();
  }

}
