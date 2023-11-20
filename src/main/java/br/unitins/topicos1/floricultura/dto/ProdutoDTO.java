package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProdutoDTO (
  @NotBlank(message = "O campo nome não pode ser nulo")
  String nome,
  String descricao,
  String codigo,
  Integer idStatusProduto,
  String imagem,
  Double precoVenda,
  Double precoCusto,
  Double desconto,
  Integer quantidadeDisponivel,
  LocalDateTime dataDisponivel,
  @NotNull(message = "O campo fornecedor não pode ser nulo")
  Long idFornecedor,
  List<Long> idsTipoProduto
) {

}
