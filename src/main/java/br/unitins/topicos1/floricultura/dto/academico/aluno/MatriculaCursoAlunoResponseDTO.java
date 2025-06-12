package br.unitins.topicos1.floricultura.dto.academico.aluno;

import java.util.List;

import br.unitins.topicos1.floricultura.model.academico.MatriculaCursoAluno;

public record MatriculaCursoAlunoResponseDTO(
    Long id,
    String turma,
    String modalidade,
    String tipoDeIngresso,
    String matriz
) {
    public static MatriculaCursoAlunoResponseDTO valueOf(MatriculaCursoAluno matricula) {


        return new MatriculaCursoAlunoResponseDTO(
            matricula.getId(),
            matricula.getTurma(),
            matricula.getModalidade(),
            matricula.getTipoDeIngresso(),
            matricula.getMatriz()
        );
    }


    public static List<MatriculaCursoAlunoResponseDTO> valueOf(List<MatriculaCursoAluno> matricula) {
    return matricula.stream()
    .map((MatriculaCursoAluno h) -> MatriculaCursoAlunoResponseDTO.valueOf(h))
    .toList();
 }
}
