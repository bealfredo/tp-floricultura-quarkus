package br.unitins.topicos1.floricultura.dto.academico.aluno;

import java.time.LocalDate;
import java.util.List;

import br.unitins.topicos1.floricultura.model.academico.Aluno;

public record AlunoResponseDTO(
    Long id,
    String matricula,
    Integer periodoAtual,
    Boolean matriculaPendente,
    String nome,
    String sobrenome,
    String login,
    String cpf,
    LocalDate dataNascimento,
    // List<MatriculaDisciplinaAlunoResponseDTO> matriculaDisciplinaAlunoResponseDTO,
    List<CursoResponseDTO> cursoResponseDTO
) {
    public static AlunoResponseDTO valueOf(Aluno aluno,  List<CursoResponseDTO> cursosResponseDTO) {
        // TelefoneResponseDTO telefoneResponseDTO = (cliente.getUsuario().getTelefone() == null) 
        //     ? null 
        //     : TelefoneResponseDTO.valueOf(cliente.getUsuario().getTelefone());


        // List<MatriculaDisciplinaAlunoResponseDTO> matriculaDisciplinaAlunoResponseDTO = matriculasDisciplinaAluno.stream()
        //     .map(MatriculaDisciplinaAlunoResponseDTO::valueOf)
        //     .toList();
        // List<CursoResponseDTO> cursoResponseDTO = cursos.stream()
        //     .map(CursoResponseDTO::valueOf)
        //     .toList();

        

        return new AlunoResponseDTO(
            aluno.getId(),
            aluno.getMatricula(),
            aluno.getPeriodoAtual(),
            aluno.getMatriculaPendente(),
            aluno.getUsuario().getNome(),
            aluno.getUsuario().getSobrenome(),
            aluno.getUsuario().getLogin(),
            aluno.getUsuario().getCpf(),
            aluno.getUsuario().getDataNascimento(),
            // matriculaDisciplinaAlunoResponseDTO,
            cursosResponseDTO
        );
    }
}
