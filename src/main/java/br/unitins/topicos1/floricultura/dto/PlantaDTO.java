package br.unitins.topicos1.floricultura.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.time.LocalDateTime;

public record PlantaDTO (
  @NotBlank(message = "O campo nomeComum não pode ser nulo")
  String nomeComum,
  String nomeCientifico,
  @Size(max = 400, message = "O campo descricao deve ter no máximo 400 caracteres")
  String descricao,
  String codigo,
  // String imagemPrincipal,
  // String[] imagens,
  @NotNull(message = "O campo precoVenda não pode ser nulo")
  Double precoVenda,
  @NotNull(message = "O campo precoCusto não pode ser nulo")
  Double precoCusto,
  @NotNull(message = "O campo desconto não pode ser nulo")
  Double desconto,
  @NotNull(message = "O campo quantidadeDisponivel não pode ser nulo")
  @Min(value = 0, message = "O campo quantidadeDisponivel deve ser maior ou igual a 0")
  Integer quantidadeDisponivel,
  // @NotNull(message = "O campo quantidadeVendido não pode ser nulo")
  // Integer quantidadeVendido,
  LocalDateTime dataDisponivel,
  String origem,
  String tempoCrescimento,
  @NotNull(message = "O campo statusPlanta não pode ser nulo")
  Integer idStatusPlanta,
  @NotNull(message = "O campo nivelDificuldade não pode ser nulo")
  Integer nivelDificuldade,
  @NotNull(message = "O campo nivelToxidade não pode ser nulo")
  Integer nivelToxidade,
  @NotNull(message = "O campo portePlanta não pode ser nulo")
  Integer portePlanta,
  List<Long> idsTags,
  @NotNull(message = "O campo idFornecedor não pode ser nulo")
  Long idFornecedor,
  @NotNull(message = "O campo idCategoriaBiologica não pode ser nulo")
  Long idCategoriaBiologica
) {

}
