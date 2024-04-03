package br.unitins.topicos1.floricultura.model.converterjpa;

import br.unitins.topicos1.floricultura.model.NivelToxidade;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NivelToxidadeConverter implements AttributeConverter<NivelToxidade, Integer> {

    @Override
    public Integer convertToDatabaseColumn(NivelToxidade nivelToxidade) {
        return (nivelToxidade == null ? null : nivelToxidade.getId());
    }

    @Override
    public NivelToxidade convertToEntityAttribute(Integer id) {
        return NivelToxidade.valueOf(id);
    }
}
