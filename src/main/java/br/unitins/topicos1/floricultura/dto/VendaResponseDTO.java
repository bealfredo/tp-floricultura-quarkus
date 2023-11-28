package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.topicos1.floricultura.model.Venda;

public record VendaResponseDTO(
    Long id,
    LocalDateTime data,
    String codigo,
    String chavePix,
    Double totalPedido,
    UsuarioResponseDTO usuario,
    List<ItemVendaResponseDTO> getItensVenda
  
) {
  public static VendaResponseDTO valueOf(Venda venda) {

    return new VendaResponseDTO(
      venda.getId(),
      venda.getDataHora(),
      venda.getCodigo(),
      venda.getChavePix(),
      venda.getTotalVenda(),
      UsuarioResponseDTO.valueOf(venda.getUsuario()),
      ItemVendaResponseDTO.valueOf(venda.getItensVenda())
    );
  }
}
