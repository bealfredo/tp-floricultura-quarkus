package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.Admin;
import br.unitins.topicos1.floricultura.model.TipoAdmin;
import br.unitins.topicos1.floricultura.model.Usuario;

public record AdminResponseDTO(
    Long id,
    TipoAdmin tipoAdmin,
    Usuario usuario
) {
    public static AdminResponseDTO valueOf(Admin admin){

        return new AdminResponseDTO(
            admin.getId(), 
            admin.getTipoAdmin(),
            admin.getUsuario());
    }
}
