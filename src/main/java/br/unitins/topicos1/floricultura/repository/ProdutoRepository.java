package br.unitins.topicos1.floricultura.repository;

import java.util.List;

import br.unitins.topicos1.floricultura.model.Produto;
import br.unitins.topicos1.floricultura.model.StatusProduto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto>{
    public List<Produto> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%"+nome+"%").list();
    }

    public Produto findByCodigo(String codigo) {
        try {
            return find("codigo = ?1 ", codigo ).singleResult();
        } catch (NoResultException e) {
            return null;
        }
        
    }

    public Produto findByIdComBloqueioCompartilhado(Long id) {
        PanacheQuery<Produto> query = find("id = ?1", id);
        query.withLock(LockModeType.PESSIMISTIC_READ);

        try {
            return query.singleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Produto> findByFornecedor(Long id) {
        return find("fornecedor.id = ?1", id).list();
    }

    public List<Produto> findByStatusProduto(StatusProduto statusProduto) {
        return find("statusProduto = ?1", statusProduto).list();
    }

    public List<Produto> findByTipoProduto(Long id){
        return find("SELECT p FROM Produto p JOIN p.tipoProduto tp WHERE tp.id = ?1", id).list();
    }
}
