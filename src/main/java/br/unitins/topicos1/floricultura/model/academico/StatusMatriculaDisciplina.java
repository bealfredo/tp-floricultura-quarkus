package br.unitins.topicos1.floricultura.model.academico;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.unitins.topicos1.floricultura.dto.academico.aluno.StatusMatriculaDisciplinaResponseDTO;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusMatriculaDisciplina {

    APROVADO(1, "Aprovado", "O aluno foi aprovado na disciplina."),
    REPROVADO(2, "Reprovado", "O aluno foi reprovado na disciplina."),
    MATRICULADO(3, "Matriculado", "O aluno está matriculado na disciplina."),
    TRANCADO(4, "Trancado", "O aluno trancou a matrícula na disciplina."),
    PENDENTE(5, "Pendente", "A matrícula do aluno está pendente.");

    private final Integer id;
    private final String label;
    private final String description;

    StatusMatriculaDisciplina(Integer id, String label, String description) {
        this.id = id;
        this.label = label;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public static StatusMatriculaDisciplina valueOf(Integer id) {
    if (id == null)
        return null;
    for (StatusMatriculaDisciplina item : StatusMatriculaDisciplina.values()) {
        if (item.getId().equals(id))
            return item;
    }
    throw new IllegalArgumentException("Id inválido para StatusMatriculaDisciplina: " + id);
}

    public static List<StatusMatriculaDisciplinaResponseDTO> listAll() {
        return Arrays.stream(StatusMatriculaDisciplina.values())
            .map(status -> new StatusMatriculaDisciplinaResponseDTO(status.getId(), status.getLabel(), status.getDescription()))
            .collect(Collectors.toList());
    }
}
