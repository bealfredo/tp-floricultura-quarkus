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
    String imagemPrincipal,
    String[] imagens,
    String login,
    String cpf,
    LocalDate dataNascimento,

    String corRaca,
    String uf,
    String cidade,
    String bairro,
    String cep,
    String logradouro,
    String numero,
    String complemento,
    String emailPessoal,
    String telefoneCelular1,
    String telefoneCelular2,
    String telefoneFixo,

    // List<MatriculaDisciplinaAlunoResponseDTO> matriculaDisciplinaAlunoResponseDTO,
    List<CursoResponseDTO> cursos

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
            aluno.getImagemPrincipal(),
            aluno.getImagens() != null ? aluno.getImagens() : new String[0],
            aluno.getUsuario().getLogin(),
            aluno.getUsuario().getCpf(),
            aluno.getUsuario().getDataNascimento(),

            aluno.getCorRaca(),
            aluno.getUf(),
            aluno.getCidade(),
            aluno.getBairro(),
            aluno.getCep(),
            aluno.getLogradouro(),
            aluno.getNumero(),
            aluno.getComplemento(),
            aluno.getEmailPessoal(),
            aluno.getTelefoneCelular1(),
            aluno.getTelefoneCelular2(),
            aluno.getTelefoneFixo(),
            // matriculaDisciplinaAlunoResponseDTO,
            cursosResponseDTO
        );
    }
}
