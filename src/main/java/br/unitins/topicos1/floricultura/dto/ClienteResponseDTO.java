package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.topicos1.floricultura.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String sobreNome,
    String email,
    String cpf,
    LocalDate dataNascimento,
    String carrinho,
    String senha,
    TelefoneResponseDTO telefone,
    List<EnderecoResponseDTO> listaEndereco
) {
    public static ClienteResponseDTO valueOf(Cliente cliente){
        TelefoneResponseDTO telefoneResponseDTO = (cliente.getUsuario().getTelefone() == null) 
            ? null 
            : TelefoneResponseDTO.valueOf(cliente.getUsuario().getTelefone());

        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getUsuario().getNome(),
            cliente.getUsuario().getSobrenome(),
            cliente.getUsuario().getLogin(),
            cliente.getUsuario().getCpf(),
            cliente.getUsuario().getDataNascimento(),
            cliente.getCarrinho(),
            cliente.getUsuario().getSenha(),
            telefoneResponseDTO,
            EnderecoResponseDTO.valueOf(cliente.getListaEndereco())
        );
    }
}
