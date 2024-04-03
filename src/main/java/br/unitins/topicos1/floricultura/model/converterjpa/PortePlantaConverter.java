package br.unitins.topicos1.floricultura.model.converterjpa;

import br.unitins.topicos1.floricultura.model.PortePlanta;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PortePlantaConverter implements AttributeConverter<PortePlanta, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PortePlanta portePlanta) {
        return (portePlanta == null ? null : portePlanta.getId());
    }

    @Override
    public PortePlanta convertToEntityAttribute(Integer id) {
        return PortePlanta.valueOf(id);
    }
}
