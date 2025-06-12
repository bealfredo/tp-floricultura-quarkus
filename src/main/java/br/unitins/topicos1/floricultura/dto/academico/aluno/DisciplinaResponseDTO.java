package br.unitins.topicos1.floricultura.dto.academico.aluno;

import java.util.List;

import br.unitins.topicos1.floricultura.model.academico.Disciplina;

public record DisciplinaResponseDTO(
    Long id,
    String nome,
    String codigo,
    List<MatriculaDisciplinaAlunoResponseDTO> matriculas

) {
    public static DisciplinaResponseDTO valueOf(Disciplina disciplina) {
        return new DisciplinaResponseDTO(
            disciplina.getId(),
            disciplina.getNome(),
            disciplina.getCodigo(),
            MatriculaDisciplinaAlunoResponseDTO.valueOf(disciplina.getMatriculasDisciplinaAluno())

        );
    }


    public static List<DisciplinaResponseDTO> valueOf(List<Disciplina> disciplina) {
    return disciplina.stream()
    .map((Disciplina h) -> DisciplinaResponseDTO.valueOf(h))
    .toList();
 }
}
