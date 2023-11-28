package br.unitins.topicos1.floricultura.repository;

import java.util.List;

import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.model.Venda;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VendaRepository implements PanacheRepository<Venda> {
  public List<Venda> findByUsuario(Usuario usuario) {
    return find("usuario = ?1", usuario).list();
  }

  // public List<TipoProduto> findByCategoriaProduto(CategoriaProduto categoriaProduto) {
  //   return find("categoriaProduto = ?1", categoriaProduto).list();
  // }
}
