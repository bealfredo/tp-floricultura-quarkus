package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.topicos1.floricultura.model.Telefone;
import br.unitins.topicos1.floricultura.model.TipoUsuario;
import br.unitins.topicos1.floricultura.model.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String sobreNome,
    String login,
    String cpf,
    LocalDate dataNascimento,
    Telefone telefone,
    List<EnderecoResponseDTO> listaEndereco,
    TipoUsuario tipoUsuario
) { 
    public static UsuarioResponseDTO valueOf(Usuario usuario){

        return new UsuarioResponseDTO(
            usuario.getId(), 
            usuario.getNome(),
            usuario.getSobreNome(),
            usuario.getLogin(),
            usuario.getCpf(),
            usuario.getDataNascimento(),
            usuario.getTelefone(),
            usuario.getListaEndereco()
                .stream()
                .map(e -> EnderecoResponseDTO.valueOf(e)).toList(),
            usuario.getTipoUsuario()
        );
    }
}
