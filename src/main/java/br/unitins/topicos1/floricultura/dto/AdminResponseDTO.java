package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;

import br.unitins.topicos1.floricultura.model.Admin;
import br.unitins.topicos1.floricultura.model.TipoPerfil;

public record AdminResponseDTO(
    Integer idTipoPerfil,
    Long id,
    String nome,
    String sobreNome,
    String email,
    String cpf,
    LocalDate dataNascimento,
    TelefoneResponseDTO telefone,
    TipoAdminResponseDTO tipoAdmin
) {
    public static AdminResponseDTO valueOf(Admin admin){
        TelefoneResponseDTO telefoneResponseDTO = (admin.getUsuario().getTelefone() == null) 
            ? null 
            : TelefoneResponseDTO.valueOf(admin.getUsuario().getTelefone());

        return new AdminResponseDTO(
            TipoPerfil.valueOf(admin.getTipoAdmin().getId()).getId(),
            admin.getId(),
            admin.getUsuario().getNome(),
            admin.getUsuario().getSobrenome(),
            admin.getUsuario().getLogin(),
            admin.getUsuario().getCpf(),
            admin.getUsuario().getDataNascimento(),
            telefoneResponseDTO,
            TipoAdminResponseDTO.valueOf(admin.getTipoAdmin())
        );
    }
}