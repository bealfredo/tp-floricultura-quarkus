package br.unitins.topicos1.floricultura.repository;

import java.util.List;

import br.unitins.topicos1.floricultura.model.CategoriaProduto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaProdutoRepository implements PanacheRepository<CategoriaProduto> {
  public List<CategoriaProduto> findByNome(String nome) {
    return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
  }
}
