package br.unitins.topicos1.floricultura.dto;

import br.unitins.topicos1.floricultura.model.Fornecedor;

public record FornecedorResponseDTO(
  Long id,
  String nome,
  String email,
  String telefone,
  String cnpj
) { 

  public static FornecedorResponseDTO valueOf(Fornecedor fornecedor) {
    return new FornecedorResponseDTO(
      fornecedor.getId(),
      fornecedor.getNome(),
      fornecedor.getEmail(),
      fornecedor.getTelefone(),
      fornecedor.getCnpj()
    );
  }

}
