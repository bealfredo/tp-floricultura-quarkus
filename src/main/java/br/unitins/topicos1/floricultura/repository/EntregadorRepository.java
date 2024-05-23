package br.unitins.topicos1.floricultura.repository;

import br.unitins.topicos1.floricultura.model.Entregador;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntregadorRepository implements PanacheRepository<Entregador>{
  public Entregador findByLogin(String login) {
    return find("LOWER(usuario.login) = LOWER(?1)", login.toLowerCase()).firstResult();
  }

}
