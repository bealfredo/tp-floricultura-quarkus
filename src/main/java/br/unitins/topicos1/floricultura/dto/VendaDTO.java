package br.unitins.topicos1.floricultura.dto;

import java.util.List;
import jakarta.validation.constraints.Size;

public record VendaDTO (
  Long idEnderecoEntrega,
  @Size(min = 1)
  List<ItemVendaDTO> itensVenda
) {

}
