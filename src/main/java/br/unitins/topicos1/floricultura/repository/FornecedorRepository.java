package br.unitins.topicos1.floricultura.repository;

import java.util.List;

import br.unitins.topicos1.floricultura.model.Fornecedor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {
  public List<Fornecedor> findByNome(String nome) {
    return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
  }

  public List<Fornecedor> findByCnpj(String cnpj) {
    try {
      return find("cnpj LIKE ?1 ", "%"+cnpj+"%").list();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<Fornecedor> findByEmail(String email) {
    return find("email LIKE ?1 ", "%"+email+"%").list();
  }
}
