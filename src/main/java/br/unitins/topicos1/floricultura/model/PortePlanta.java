package br.unitins.topicos1.floricultura.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitins.topicos1.floricultura.dto.PortePlantaResponseDTO;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PortePlanta {
  
  PEQUENO(1, "Pequeno", "Ideal para espaços internos ou pequenos jardins, esta planta normalmente não ultrapassa 30 cm de altura. É perfeita para quem tem espaço limitado ou deseja decorar mesas e prateleiras."),
  MEDIO(2, "Médio", "Adequada para ambientes internos espaçosos e jardins externos, esta planta atinge entre 30 cm e 1 m de altura. Requer um pouco mais de espaço para crescer, mas ainda é manejável para a maioria dos ambientes residenciais."),
  GRANDE(3, "Grande", "Ideal para jardins amplos ou como peça central em espaços grandes, esta planta pode ultrapassar 1 m de altura. Requer mais espaço e em alguns casos cuidados especiais para garantir seu pleno desenvolvimento.");

  private final Integer id;
  private final String label;
  private final String description;

  PortePlanta(Integer id, String label, String description) {
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

  public static PortePlanta valueOf(Integer id) {
    if (id == null)
      return null;
    for (PortePlanta item : PortePlanta.values()) {
      if (item.getId().equals(id))
        return item;
    }
    return null;
  }

  public static List<PortePlantaResponseDTO> listAll() {
        return Arrays.stream(PortePlanta.values())
            .map(portePlanta -> new PortePlantaResponseDTO(portePlanta.getId(), portePlanta.getLabel(), portePlanta.getDescription()))
            .collect(Collectors.toList());
    }
}
