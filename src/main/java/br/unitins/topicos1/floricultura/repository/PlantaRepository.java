package br.unitins.topicos1.floricultura.repository;

import java.util.List;

import br.unitins.topicos1.floricultura.model.Planta;
import br.unitins.topicos1.floricultura.model.StatusPlanta;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;

@ApplicationScoped 
public class PlantaRepository implements PanacheRepository<Planta>{
    public List<Planta> findByNome(String nome) {
        if (nome == null) {
            return null;
          }
        return find("LOWER(nomeComum) LIKE LOWER(?1) OR LOWER(nomeCientifico) LIKE LOWER(?1)", "%" + nome + "%").list();
    }

    public Planta findByCodigo(String codigo) {
        try {
            return find("codigo = ?1 ", codigo ).singleResult();
        } catch (NoResultException e) {
            return null;
        } 
    }

    public Planta findByIdComBloqueioCompartilhado(Long id) {
        PanacheQuery<Planta> query = find("id = ?1", id);
        query.withLock(LockModeType.PESSIMISTIC_READ);

        try {
            return query.singleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Planta> findByFornecedor(Long id) {
        return find("fornecedor.id = ?1", id).list();
    }

    public List<Planta> findByStatusPlanta(StatusPlanta statusPlanta) {
        return find("statusPlanta = ?1", statusPlanta).list();
    }

    public List<Planta> findByNivelDificuldade(Long id){
        return find("nivelDificuldade = ?1", id).list();
    }

    public List<Planta> findByNivelToxidade(Long id){
        return find("nivelToxidade = ?1", id).list();
    }

    public List<Planta> findByPortePlanta(String porte) {

        return find("porte = ?1", porte).list();
    }

    public List<Planta> findByCategoriaPlanta(Long id){
        return find("categoriaPlanta = ?1", id).list();
    }

    public List<Planta> findByTag(Long id){
        return find("SELECT p FROM Planta p JOIN p.tag tp WHERE tp.id = ?1", id).list();
    }
}
