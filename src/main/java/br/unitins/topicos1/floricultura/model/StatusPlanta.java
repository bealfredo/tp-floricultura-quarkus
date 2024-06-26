package br.unitins.topicos1.floricultura.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitins.topicos1.floricultura.dto.StatusPlantaResponseDTO;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusPlanta {
  
  RASCUNHO(1, "Rascunho", "Planta em fase de desenvolvimento e não disponível para venda."),
  ATIVO(2, "Ativo", "Planta ativa e disponível para venda."),
  INATIVO(3, "Inativo", "Planta inativa e indisponível para venda."),
  ARQUIVADO(4, "Arquivado", "Planta arquivada e não disponível para venda.");

  private final Integer id;
  private final String label;
  private final String description;

  StatusPlanta(Integer id, String label, String description) {
    this.id = id;
    this.label = label;
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public String getDescription() {
    return description;
  }

  public static StatusPlanta valueOf(Integer id) {
    if (id == null)
      return null;
    for (StatusPlanta item : StatusPlanta.values()) {
      if (item.getId().equals(id))
        return item;
    }
    return null;
  }

  public static List<StatusPlantaResponseDTO> listAll() {
        return Arrays.stream(StatusPlanta.values())
            .map(statusPlanta -> new StatusPlantaResponseDTO(statusPlanta.getId(), statusPlanta.getLabel(), statusPlanta.getDescription()))
            .collect(Collectors.toList());
    }
}
