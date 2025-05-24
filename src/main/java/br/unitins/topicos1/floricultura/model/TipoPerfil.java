package br.unitins.topicos1.floricultura.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitins.topicos1.floricultura.dto.TipoPerfilResponseDTO;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoPerfil {

  OWNER(1, "Owner", "Administrador com permissão total."),
  EMPLOYEE(2, "Employee", "Administrador com permissão limitada."),
  CUSTOMER(3, "Customer", "Cliente no sistema"),
  DELIVERYMAN(4, "Deliveryman", "Entregador no sistema");

  private final Integer id;
  private final String label;
  private final String description;

  TipoPerfil(Integer id, String label, String description) {
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

  public static TipoPerfil valueOf(Integer id) {
    if (id == null)
      return null;
    for (TipoPerfil item : TipoPerfil.values()) {
      if (item.getId().equals(id))
        return item;
    }
    return null;
  }

  public static List<TipoPerfilResponseDTO> listAll() {
        return Arrays.stream(TipoPerfil.values())
            .map(tipoPerfil -> new TipoPerfilResponseDTO(tipoPerfil.getId(), tipoPerfil.getLabel(), tipoPerfil.getDescription()))
            .collect(Collectors.toList());
    }
}
