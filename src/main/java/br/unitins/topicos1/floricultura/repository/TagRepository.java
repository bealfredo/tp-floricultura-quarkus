package br.unitins.topicos1.floricultura.repository;

import java.util.List;

import br.unitins.topicos1.floricultura.model.CategoriaPlanta;
import br.unitins.topicos1.floricultura.model.Tag;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TagRepository implements PanacheRepository<Tag> {
  public PanacheQuery<Tag> findByNome(String nome, boolean caseSensitive) {
    if (nome == null) {
      return null;
    }
    if (caseSensitive) {
      return find("nome", nome);
    } else {
      return find("UPPER(nome) LIKE UPPER(?1) ", "%" + nome + "%");
    }
  }

  public List<Tag> findByAtiva(boolean ativa) {
    return find("ativa", ativa).list();
  }

  public List<Tag> findByPrioridade() {
    return find("order by prioridade asc").list();
  }

  public List<Tag> findByCategoriaPlanta(CategoriaPlanta categoriaPlanta) {
    return find("categoriaPlanta = ?1", categoriaPlanta).list();
  }
}
