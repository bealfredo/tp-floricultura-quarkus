package br.unitins.topicos1.floricultura.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitins.topicos1.floricultura.dto.StatusProdutoResponseDTO;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusProduto {

    ATIVO(1, "Ativo"),
    INATIVO(2, "Inativo"),
    ARQUIVADO(3, "Arquivado");

    private final Integer id;
    private final String label;

    StatusProduto(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static StatusProduto valueOf(Integer id) {
        if (id == null)
            return null;
        for (StatusProduto status : StatusProduto.values()) {
            if (status.getId().equals(id))
                return status;
        }
        return null;
    }

    public static List<StatusProdutoResponseDTO> listAll() {
        return Arrays.stream(StatusProduto.values())
            .map(status -> new StatusProdutoResponseDTO(status.getId(), status.getLabel()))
            .collect(Collectors.toList());
    }
}
