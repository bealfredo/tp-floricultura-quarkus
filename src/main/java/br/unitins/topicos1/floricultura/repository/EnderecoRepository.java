package br.unitins.topicos1.floricultura.repository;

import br.unitins.topicos1.floricultura.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco>{
  

}
