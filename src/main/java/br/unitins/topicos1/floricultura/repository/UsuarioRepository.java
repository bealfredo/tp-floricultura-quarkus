package br.unitins.topicos1.floricultura.repository;

import br.unitins.topicos1.floricultura.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario findByLogin(String login) {
        if (login == null)
            return null;
            
        return find("LOWER(login) = LOWER(?1)", login.toLowerCase()).firstResult();
    }

}
