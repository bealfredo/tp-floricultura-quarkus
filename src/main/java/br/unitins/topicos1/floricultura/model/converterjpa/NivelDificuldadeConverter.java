package br.unitins.topicos1.floricultura.model.converterjpa;

import br.unitins.topicos1.floricultura.model.NivelDificuldade;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NivelDificuldadeConverter implements AttributeConverter<NivelDificuldade, Integer> {

    @Override
    public Integer convertToDatabaseColumn(NivelDificuldade nivelDificuldade) {
        return (nivelDificuldade == null ? null : nivelDificuldade.getId());
    }

    @Override
    public NivelDificuldade convertToEntityAttribute(Integer id) {
        return NivelDificuldade.valueOf(id);
    }
}
