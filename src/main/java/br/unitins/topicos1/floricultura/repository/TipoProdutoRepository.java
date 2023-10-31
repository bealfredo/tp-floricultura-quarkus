package br.unitins.topicos1.floricultura.repository;

import java.util.List;

import br.unitins.topicos1.floricultura.model.CategoriaProduto;
import br.unitins.topicos1.floricultura.model.TipoProduto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TipoProdutoRepository implements PanacheRepository<TipoProduto> {
  public List<TipoProduto> findByNome(String nome) {
    return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
  }

  // public List<TipoProduto> findByCategoriaProduto(Long categoriaProduto) {
  //   return find("id_categoriaproduto = (?1) ", categoriaProduto).list();
  // }

  public List<TipoProduto> findByCategoriaProduto(CategoriaProduto categoriaProduto) {
    return find("categoriaProduto = ?1", categoriaProduto).list();
  }
}
