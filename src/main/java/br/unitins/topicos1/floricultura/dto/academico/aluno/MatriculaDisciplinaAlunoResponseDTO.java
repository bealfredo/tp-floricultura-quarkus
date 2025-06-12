package br.unitins.topicos1.floricultura.dto.academico.aluno;

import java.util.List;

import br.unitins.topicos1.floricultura.model.academico.MatriculaDisciplinaAluno;

public record MatriculaDisciplinaAlunoResponseDTO(
    Long id,
    Double a1,
    Double a2,
    Double exameFinal,
    Double frequencia,
    Double mediaFinal,
    String statusMatriculaDisciplina
) {
    public static MatriculaDisciplinaAlunoResponseDTO valueOf(MatriculaDisciplinaAluno matricula) {
        return new MatriculaDisciplinaAlunoResponseDTO(
            matricula.getId(),
            matricula.getA1(),
            matricula.getA2(),
            matricula.getExameFinal(),
            matricula.getFrequencia(),
            matricula.getMediaFinal(),
            matricula.getStatusMatriculaDisciplina()
        );
    }


    public static List<MatriculaDisciplinaAlunoResponseDTO> valueOf(List<MatriculaDisciplinaAluno> matricula) {
    return matricula.stream()
    .map((MatriculaDisciplinaAluno h) -> MatriculaDisciplinaAlunoResponseDTO.valueOf(h))
    .toList();
 }
}
