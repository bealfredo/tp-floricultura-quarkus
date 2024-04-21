package br.unitins.topicos1.floricultura.model;

import com.fasterxml.jackson.annotation.JsonFormat;

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
}
