package br.unitins.topicos1.floricultura.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.topicos1.floricultura.model.HistoricoStatus;

public record HistoricoStatusResponseDTO(
    Long id,
    LocalDateTime data,
    StatusVendaResponseDTO statusVenda) {
  public static HistoricoStatusResponseDTO valueOf(HistoricoStatus historico) {

    return new HistoricoStatusResponseDTO(
        historico.getId(),
        historico.getData(),
        StatusVendaResponseDTO.valueOf(historico.getStatusVenda())
    );
  }

  public static List<HistoricoStatusResponseDTO> valueOf(List<HistoricoStatus> historico) {
    return historico.stream()
    .map((HistoricoStatus h) -> HistoricoStatusResponseDTO.valueOf(h))
    .toList();
 }
}
