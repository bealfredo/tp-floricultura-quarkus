package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.topicos1.floricultura.model.Cliente;
import br.unitins.topicos1.floricultura.model.TipoPerfil;

public record ClienteResponseDTO(
    Integer idTipoPerfil,
    Long id,
    String nome,
    String sobrenome,
    String email,
    String cpf,
    LocalDate dataNascimento,
    String carrinho,
    TelefoneResponseDTO telefone,
    List<EnderecoResponseDTO> listaEndereco
) {
    public static ClienteResponseDTO valueOf(Cliente cliente){
        TelefoneResponseDTO telefoneResponseDTO = (cliente.getUsuario().getTelefone() == null) 
            ? null 
            : TelefoneResponseDTO.valueOf(cliente.getUsuario().getTelefone());

        return new ClienteResponseDTO(
            TipoPerfil.CUSTOMER.getId(),
            cliente.getId(),
            cliente.getUsuario().getNome(),
            cliente.getUsuario().getSobrenome(),
            cliente.getUsuario().getLogin(),
            cliente.getUsuario().getCpf(),
            cliente.getUsuario().getDataNascimento(),
            cliente.getCarrinho(),
            telefoneResponseDTO,
            EnderecoResponseDTO.valueOf(cliente.getListaEndereco())
        );
    }
}
