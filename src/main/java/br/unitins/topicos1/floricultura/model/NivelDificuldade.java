package br.unitins.topicos1.floricultura.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NivelDificuldade {
  
  FACIL(1, "Fácil", "Planta de fácil cultivo."),
  MODERADO(2, "Moderado", "Planta de cultivo moderado."),
  DIFICIL(3, "Difícil", "Planta de cultivo difícil."),
  ESPECIALISTA(4, "Especialista", "Planta de cultivo à nível de especialista.");

  private final Integer id;
  private final String label;
  private final String description;

  NivelDificuldade(Integer id, String label, String description) {
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

  public static NivelDificuldade valueOf(Integer id) {
    if (id == null)
      return null;
    for (NivelDificuldade item : NivelDificuldade.values()) {
      if (item.getId().equals(id))
        return item;
    }
    return null;
  }
}
