package br.unitins.topicos1.floricultura.model.converterjpa;

import br.unitins.topicos1.floricultura.model.TipoPerfil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoPerfilConverter implements AttributeConverter<TipoPerfil, Integer> {

  @Override
  public Integer convertToDatabaseColumn(TipoPerfil ta) {
    return (ta == null ? null : ta.getId());
  }

  @Override
  public TipoPerfil convertToEntityAttribute(Integer id) {
    return TipoPerfil.valueOf(id);
  }
  
}

