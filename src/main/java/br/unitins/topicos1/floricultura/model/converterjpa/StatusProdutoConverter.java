package br.unitins.topicos1.floricultura.model.converterjpa;

import br.unitins.topicos1.floricultura.model.StatusProduto;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusProdutoConverter implements AttributeConverter<StatusProduto, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusProduto statusProduto) {
        return (statusProduto == null ? null : statusProduto.getId());
    }

    @Override
    public StatusProduto convertToEntityAttribute(Integer id) {
        return StatusProduto.valueOf(id);
    }
}
