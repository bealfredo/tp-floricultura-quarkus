package br.unitins.topicos1.floricultura.dto;

public record UsuarioTiposPerfilByEmailResponseDTO(
    String email,
    UsuarioPerfilByEmailResponseDTO owner,
    UsuarioPerfilByEmailResponseDTO employee,
    UsuarioPerfilByEmailResponseDTO customer,
    UsuarioPerfilByEmailResponseDTO deliveryman
) {
    public static UsuarioTiposPerfilByEmailResponseDTO valueOf(String email, Long owner, Long employee, Long customer, Long deliveryman){
        return new UsuarioTiposPerfilByEmailResponseDTO(
            email,
            UsuarioPerfilByEmailResponseDTO.valueOf(owner),
            UsuarioPerfilByEmailResponseDTO.valueOf(employee),
            UsuarioPerfilByEmailResponseDTO.valueOf(customer),
            UsuarioPerfilByEmailResponseDTO.valueOf(deliveryman)
        );
    }
}
