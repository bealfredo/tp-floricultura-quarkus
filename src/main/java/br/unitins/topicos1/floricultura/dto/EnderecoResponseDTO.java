package br.unitins.topicos1.floricultura.dto;

import java.util.List;

import br.unitins.topicos1.floricultura.model.Cidade;
import br.unitins.topicos1.floricultura.model.Endereco;

public record EnderecoResponseDTO(
    Long id,
    String nome,
    String cep,
    String rua,
    String bairro,
    String numeroLote,
    String complemento,
    Cidade cidade
) {
    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        return new EnderecoResponseDTO(
            endereco.getId(),
            endereco.getNome(),
            endereco.getCep(),
            endereco.getRua(),
            endereco.getBairro(),
            endereco.getNumeroLote(),
            endereco.getComplemento(),
            endereco.getCidade()
        );
    }

    public static List<EnderecoResponseDTO> valueOf(List<Endereco> endereco) {
        return endereco.stream()
        .map((Endereco e) -> EnderecoResponseDTO.valueOf(e))
        .toList();
    }
}
