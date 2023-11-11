package br.unitins.topicos1.floricultura.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoUsuario {
  
  CLIENTE(1, "Cliente"),
  ADMIN(2, "Admin");

  private final Integer id;
  private final String label;


  TipoUsuario(Integer id, String label) {
    this.id = id;
    this.label = label;
  }

  public Integer getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public static TipoUsuario valueOf(Integer id) throws IllegalArgumentException {
    if (id == null)
      return null;
    for (TipoUsuario perfil : TipoUsuario.values()) {
      if (perfil.getId().equals(id))
        return perfil;
    }
    throw new IllegalArgumentException("id inválido " + id);
  }

}
