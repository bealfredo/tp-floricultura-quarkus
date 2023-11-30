package br.unitins.topicos1.floricultura.model.converterjpa;

import br.unitins.topicos1.floricultura.model.StatusVenda;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusVendaConverter implements AttributeConverter<StatusVenda, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusVenda statusVenda) {
        return (statusVenda == null ? null : statusVenda.getId());
    }

    @Override
    public StatusVenda convertToEntityAttribute(Integer id) {
        return StatusVenda.valueOf(id);
    }
}
