package br.unitins.topicos1.floricultura.repository;

import java.util.List;

import br.unitins.topicos1.floricultura.model.CategoriaPlanta;
import br.unitins.topicos1.floricultura.model.TipoCategoria;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaPlantaRepository implements PanacheRepository<CategoriaPlanta> {
  public PanacheQuery<CategoriaPlanta> findByNome(String nome, boolean caseSensitive) {
    if (nome == null) {
      return null;
    }
    if (caseSensitive) {
      return find("nome", nome);
    } else {
      return find("UPPER(nome) LIKE UPPER(?1) ", "%" + nome + "%");
    }
    // return find("UPPER(nome) LIKE UPPER(?1) ", "%" + nome + "%");
  }

  public List<CategoriaPlanta> findByAtiva(boolean ativa) {
    return find("ativa", ativa).list();
  }

  public List<CategoriaPlanta> findByPrioridade() {
    return find("order by prioridade asc").list();
  }

   public List<CategoriaPlanta> findByTipoCategoria(TipoCategoria tipoCategoria) {
    return find("tipoCategoria = ?1", tipoCategoria).list();
  }


}
