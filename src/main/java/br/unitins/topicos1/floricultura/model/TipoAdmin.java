package br.unitins.topicos1.floricultura.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitins.topicos1.floricultura.dto.TipoAdminResponseDTO;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoAdmin {

  OWNER(1, "Owner", "Administrador com permissão total."),
  EMPLOYEE(2, "Employee", "Administrador com permissão limitada.");

  private final Integer id;
  private final String label;
  private final String description;

  TipoAdmin(Integer id, String label, String description) {
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

  public static TipoAdmin valueOf(Integer id) {
    if (id == null)
      return null;
    for (TipoAdmin item : TipoAdmin.values()) {
      if (item.getId().equals(id))
        return item;
    }
    return null;
  }

  public static List<TipoAdminResponseDTO> listAll() {
        return Arrays.stream(TipoAdmin.values())
            .map(tipoAdmin -> new TipoAdminResponseDTO(tipoAdmin.getId(), tipoAdmin.getLabel(), tipoAdmin.getDescription()))
            .collect(Collectors.toList());
    }
}
