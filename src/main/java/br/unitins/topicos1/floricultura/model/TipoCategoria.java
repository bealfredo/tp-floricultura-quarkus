package br.unitins.topicos1.floricultura.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoCategoria {

  BIOLOGICA(1, "Biologica", "Categoria biológica para itens baseados em características biológicas."),
  VIEW(2, "View", "Categoria de visualização para fins de exibição na aplicação.");

  private final Integer id;
  private final String label;
  private final String description;

  TipoCategoria(Integer id, String label, String description) {
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

  public static TipoCategoria valueOf(Integer id) {
    if (id == null)
      return null;
    for (TipoCategoria item : TipoCategoria.values()) {
      if (item.getId().equals(id))
        return item;
    }
    return null;
  }
}
