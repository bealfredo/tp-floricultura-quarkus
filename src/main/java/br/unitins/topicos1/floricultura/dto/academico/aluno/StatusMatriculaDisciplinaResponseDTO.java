package br.unitins.topicos1.floricultura.dto.academico.aluno;

import br.unitins.topicos1.floricultura.model.academico.StatusMatriculaDisciplina;

public record StatusMatriculaDisciplinaResponseDTO (
  Integer id,
  String label,
  String description
) { 

  public static StatusMatriculaDisciplinaResponseDTO valueOf(StatusMatriculaDisciplina statusMatriculaDisciplina) {
    return new StatusMatriculaDisciplinaResponseDTO(
      statusMatriculaDisciplina.getId(),
      statusMatriculaDisciplina.getLabel(),
      statusMatriculaDisciplina.getDescription()
    );
  }

}
