package br.unitins.topicos1.floricultura.repository;

import br.unitins.topicos1.floricultura.model.ItemVenda;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemVendaRepository implements PanacheRepository<ItemVenda> {
  // public List<TipoProduto> findByNome(String nome) {
  //   return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
  // }

  // public List<TipoProduto> findByCategoriaProduto(CategoriaProduto categoriaProduto) {
  //   return find("categoriaProduto = ?1", categoriaProduto).list();
  // }
}
