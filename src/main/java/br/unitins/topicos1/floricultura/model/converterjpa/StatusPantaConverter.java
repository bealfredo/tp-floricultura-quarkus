package br.unitins.topicos1.floricultura.model.converterjpa;

import br.unitins.topicos1.floricultura.model.StatusPlanta;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusPantaConverter implements AttributeConverter<StatusPlanta, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusPlanta statusPlanta) {
        return (statusPlanta == null ? null : statusPlanta.getId());
    }

    @Override
    public StatusPlanta convertToEntityAttribute(Integer id) {
        return StatusPlanta.valueOf(id);
    }
}
