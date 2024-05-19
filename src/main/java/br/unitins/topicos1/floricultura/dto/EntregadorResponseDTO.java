package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;

import br.unitins.topicos1.floricultura.model.Entregador;

public record EntregadorResponseDTO(
    Long id,
    String nome,
    String sobreNome,
    String email,
    String cpf,
    LocalDate dataNascimento,
    TelefoneResponseDTO telefone,
    String cnh,
    String cnpj
) {
    public static EntregadorResponseDTO valueOf(Entregador entregador){
        TelefoneResponseDTO telefoneResponseDTO = (entregador.getUsuario().getTelefone() == null) 
            ? null 
            : TelefoneResponseDTO.valueOf(entregador.getUsuario().getTelefone());

        return new EntregadorResponseDTO(
            entregador.getId(),
            entregador.getUsuario().getNome(),
            entregador.getUsuario().getSobrenome(),
            entregador.getUsuario().getLogin(),
            entregador.getUsuario().getCpf(),
            entregador.getUsuario().getDataNascimento(),
            telefoneResponseDTO,
            entregador.getCnh(),
            entregador.getCnpj()
        );
    }
}
