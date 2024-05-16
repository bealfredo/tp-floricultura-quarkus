package br.unitins.topicos1.floricultura.repository;

import java.util.List;

import br.unitins.topicos1.floricultura.model.Estado;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {
  public List<Estado> findByNome(String nome, Boolean substring) {
    if (substring) {
      return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    } else {
      return find("UPPER(nome) = UPPER(?1) ", nome).list();
    }
  }

  public List<Estado> findBySigla(String sigla, Boolean substring) {
    if (substring) {
      return find("UPPER(sigla) LIKE UPPER(?1) ", "%"+sigla+"%").list();
    } else {
      return find("UPPER(sigla) = UPPER(?1) ", sigla).list();
    }
  }
}
