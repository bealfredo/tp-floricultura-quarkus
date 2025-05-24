package br.unitins.topicos1.floricultura.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitins.topicos1.floricultura.dto.StatusVendaResponseDTO;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusVenda {

    AGUARDANDO_PAGAMENTO(1, "Aguardando pagamento"),
    PAGAMENTO_CONFIRMADO(2, "Pagamento confirmado"),
    PREPARANDO_ENVIO(3, "Preparando envio"),
    ENVIADO(4, "Enviado"),
    ENTREGUE(5, "Entregue"),
    CANCELADO(6, "Cancelada");

    private final Integer id;
    private final String label;

    StatusVenda(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static StatusVenda valueOf(Integer id) {
        if (id == null)
            return null;
        for (StatusVenda status : StatusVenda.values()) {
            if (status.getId().equals(id))
                return status;
        }
        return null;
    }

    public static List<StatusVendaResponseDTO> listAll() {
        return Arrays.stream(StatusVenda.values())
            .map(status -> new StatusVendaResponseDTO(status.getId(), status.getLabel()))
            .collect(Collectors.toList());
    }
}
