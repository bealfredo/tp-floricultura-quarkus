package br.unitins.topicos1.floricultura.repository;

import br.unitins.topicos1.floricultura.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario findByLogin(String login) {
        if (login == null)
            return null;
            
        return find("LOWER(login) = LOWER(?1)", login.toLowerCase()).firstResult();
    }

    public Usuario findByCpf(String cpf) {
        if (cpf == null)
            return null;
            
        return find("cpf = ?1", cpf).firstResult();
    }

    public Usuario findByLoginAndSenha(String login, String senha) {
        try {
            return find("login = ?1 AND senha = ?2 ", login, senha).singleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
