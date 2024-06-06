package br.unitins.topicos1.floricultura.dto;

public record UsuarioPerfilByEmailResponseDTO(
    Long id,
    Boolean hasPerfil
) {
    public static UsuarioPerfilByEmailResponseDTO valueOf(Long id){
        return new UsuarioPerfilByEmailResponseDTO(
            id,
            (id != null)
        );
    }
}
