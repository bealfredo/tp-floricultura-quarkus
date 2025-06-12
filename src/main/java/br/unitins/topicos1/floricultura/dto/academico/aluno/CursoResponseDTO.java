package br.unitins.topicos1.floricultura.dto.academico.aluno;

import java.util.List;

import br.unitins.topicos1.floricultura.model.academico.Curso;

public record CursoResponseDTO(
    Long id,
    String nome,
    String codigo,
    List<DisciplinaResponseDTO> disciplinas

) {
    public static CursoResponseDTO valueOf(Curso curso) {
        return new CursoResponseDTO(
            curso.getId(),
            curso.getNome(),
            curso.getCodigo(),
            DisciplinaResponseDTO.valueOf(curso.getDisciplinas())
        );
    }


    public static List<CursoResponseDTO> valueOf(List<Curso> curso) {
    return curso.stream()
    .map((Curso h) -> CursoResponseDTO.valueOf(h))
    .toList();
 }
}
