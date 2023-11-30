package br.unitins.topicos1.floricultura.repository;

import java.time.LocalDateTime;
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

  public int qtVendasOfDay() {
      LocalDateTime inicioDoDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
    LocalDateTime fimDoDia = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);

    return (int) find("dataHora between ?1 and ?2", inicioDoDia, fimDoDia).count();

  }

  // public List<TipoProduto> findByCategoriaProduto(CategoriaProduto categoriaProduto) {
  //   return find("categoriaProduto = ?1", categoriaProduto).list();
  // }
}
