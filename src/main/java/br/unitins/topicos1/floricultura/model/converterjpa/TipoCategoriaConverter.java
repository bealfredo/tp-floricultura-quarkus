package br.unitins.topicos1.floricultura.model.converterjpa;

import br.unitins.topicos1.floricultura.model.TipoCategoria;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoCategoriaConverter implements AttributeConverter<TipoCategoria, Integer> {

  @Override
  public Integer convertToDatabaseColumn(TipoCategoria tu) {
    return (tu == null ? null : tu.getId());
  }

  @Override
  public TipoCategoria convertToEntityAttribute(Integer id) {
    return TipoCategoria.valueOf(id);
  }
  
}

