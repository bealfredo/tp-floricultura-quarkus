package br.unitins.topicos1.floricultura.dto;

import java.util.List;

import br.unitins.topicos1.floricultura.model.ItemVenda;
import br.unitins.topicos1.floricultura.model.Produto;

public record ItemVendaResponseDTO(
    Long id,
    Double preco,
    Integer quantidade,
    Produto produto) {
  public static ItemVendaResponseDTO valueOf(ItemVenda item) {

    return new ItemVendaResponseDTO(
        item.getId(),
        item.getPreco(),
        item.getQuantidade(),
        item.getProduto());
  }

  public static List<ItemVendaResponseDTO> valueOf(List<ItemVenda> itens) {
    return itens.stream()
    .map((ItemVenda t) -> ItemVendaResponseDTO.valueOf(t))
    .toList();
 }
}
