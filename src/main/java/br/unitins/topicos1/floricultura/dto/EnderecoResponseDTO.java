package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.Cidade;
import br.unitins.topicos1.floricultura.model.Endereco;

public record EnderecoResponseDTO(
    Long id,
    String nome,
    String cep,
    String codigo,
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
            endereco.getCodigo(),
            endereco.getRua(),
            endereco.getBairro(),
            endereco.getNumeroLote(),
            endereco.getComplemento(),
            endereco.getCidade());
    }
}
