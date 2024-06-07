package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.topicos1.floricultura.model.Venda;

public record VendaResponseDTO(
    Long id,
    LocalDateTime data,
    String codigo,
    String chavePix,
    Double totalVenda,
    HistoricoStatusResponseDTO lastStatus,
    EnderecoResponseDTO endereco,
    ClienteResponseDTO cliente,
    List<ItemVendaResponseDTO> itensVenda,
    List<HistoricoStatusResponseDTO> historicoStatus
) {
  public static VendaResponseDTO valueOf(Venda venda) {

    return new VendaResponseDTO(
      venda.getId(),
      venda.getDataHora(),
      venda.getCodigo(),
      venda.getChavePix(),
      venda.getTotalVenda(),
      HistoricoStatusResponseDTO.valueOf(venda.getLastStatus()),
      EnderecoResponseDTO.valueOf(venda.getEndereco()),
      ClienteResponseDTO.valueOf(venda.getCliente()),
      ItemVendaResponseDTO.valueOf(venda.getItensVenda()),
      HistoricoStatusResponseDTO.valueOf(venda.getHistoricoStatus())
    );
  }
}
