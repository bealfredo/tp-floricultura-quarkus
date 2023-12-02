package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.topicos1.floricultura.model.TipoUsuario;
import br.unitins.topicos1.floricultura.model.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String login,
    LocalDate dataNascimento,
    List<TelefoneDTO> listaTelefone,
    TipoUsuario tipoUsuario
) { 
    public static UsuarioResponseDTO valueOf(Usuario usuario){

        return new UsuarioResponseDTO(
            usuario.getId(), 
            usuario.getNome(),
            usuario.getLogin(),
            usuario.getDataNascimento(),
            usuario.getListaTelefone()
                .stream()
                .map(t -> TelefoneDTO.valueOf(t)).toList(),
            usuario.getTipoUsuario()
        );
    }
}
