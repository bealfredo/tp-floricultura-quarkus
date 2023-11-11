package br.unitins.topicos1.floricultura.model.converterjpa;

import br.unitins.topicos1.floricultura.model.TipoUsuario;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoUsuarioConverter implements AttributeConverter<TipoUsuario, Integer> {

  @Override
  public Integer convertToDatabaseColumn(TipoUsuario tu) {
    return (tu == null ? null : tu.getId());
  }

  @Override
  public TipoUsuario convertToEntityAttribute(Integer id) {
    return TipoUsuario.valueOf(id);
  }
  
}

