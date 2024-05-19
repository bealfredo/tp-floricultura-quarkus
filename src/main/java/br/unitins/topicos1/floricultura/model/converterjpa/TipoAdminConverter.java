package br.unitins.topicos1.floricultura.model.converterjpa;

import br.unitins.topicos1.floricultura.model.TipoAdmin;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoAdminConverter implements AttributeConverter<TipoAdmin, Integer> {

  @Override
  public Integer convertToDatabaseColumn(TipoAdmin ta) {
    return (ta == null ? null : ta.getId());
  }

  @Override
  public TipoAdmin convertToEntityAttribute(Integer id) {
    return TipoAdmin.valueOf(id);
  }
  
}

