package br.unitins.topicos1.floricultura.repository;

import java.util.List;

import br.unitins.topicos1.floricultura.model.Cidade;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CidadeRepository implements PanacheRepository<Cidade> {
    public List<Cidade> findByNome(String nome, Boolean substring) {
        if (substring) {
            return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
        } else {
            return find("UPPER(nome) = UPPER(?1) ", nome).list();
        }
    }

    public List<Cidade> findByEstado(Long idEstado) {
        return find("estado.id = ?1", idEstado).list();
    }
}
