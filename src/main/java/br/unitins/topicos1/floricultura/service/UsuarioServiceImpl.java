package br.unitins.topicos1.floricultura.service;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.floricultura.dto.AdminResponseDTO;
import br.unitins.topicos1.floricultura.dto.AuthUsuarioDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableResponseDTO;
import br.unitins.topicos1.floricultura.dto.EmailTakenClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.EntregadorResponseDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioTiposPerfilByEmailResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.AlunoResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.CursoResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.DisciplinaResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.MatriculaDisciplinaAlunoResponseDTO;
import br.unitins.topicos1.floricultura.model.Admin;
import br.unitins.topicos1.floricultura.model.Cliente;
import br.unitins.topicos1.floricultura.model.Entregador;
import br.unitins.topicos1.floricultura.model.TipoAdmin;
import br.unitins.topicos1.floricultura.model.TipoPerfil;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.model.academico.Aluno;
import br.unitins.topicos1.floricultura.model.academico.Curso;
import br.unitins.topicos1.floricultura.model.academico.MatriculaDisciplinaAluno;
import br.unitins.topicos1.floricultura.repository.AdminRepository;
import br.unitins.topicos1.floricultura.repository.ClienteRepository;
import br.unitins.topicos1.floricultura.repository.EntregadorRepository;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import br.unitins.topicos1.floricultura.repository.academico.AlunoRepository;
import br.unitins.topicos1.floricultura.repository.academico.CursoRepository;
import br.unitins.topicos1.floricultura.repository.academico.MatriculaDisciplinaAlunoRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    HashService hashService;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    AdminRepository adminRepository;

    @Inject
    EntregadorRepository entregadorRepository;

    @Inject
    AlunoRepository alunoRepository;

    @Inject
    MatriculaDisciplinaAlunoRepository matriculaDisciplinaAlunoRepository;

    @Inject
    CursoRepository cursoRepository;

    @Inject
    JwtService jwtService;

    @Inject
    JsonWebToken jwt;

    private void throwTipoPerfilInvalido() {
        throw new ValidationException("idTipoPerfil", "Tipo de perfil inválido para o usuário.");
    }

    private void validTipoPerfil(Usuario usuario, AuthUsuarioDTO dto) {
        TipoPerfil tipoPerfil = TipoPerfil.valueOf(dto.idTipoPerfil());
            if (tipoPerfil == null) {
                throw new ValidationException("idTipoPerfil", "Tipo de perfil inválido.");
            }

        switch (tipoPerfil) {
            case OWNER:
                Admin admin = adminRepository.findByLogin(usuario.getLogin());
                if (admin == null)
                    throwTipoPerfilInvalido();
                if (admin.getTipoAdmin() != TipoAdmin.OWNER)
                    throwTipoPerfilInvalido();
                break;
            case EMPLOYEE:
                Admin admin2 = adminRepository.findByLogin(usuario.getLogin());
                if (admin2 == null)
                    throwTipoPerfilInvalido();
                if (admin2.getTipoAdmin() != TipoAdmin.EMPLOYEE)
                    throwTipoPerfilInvalido();
                break;
            case CUSTOMER:
                if (clienteRepository.findByLogin(usuario.getLogin()) == null)
                    throwTipoPerfilInvalido();
                break;
            case DELIVERYMAN:
                if (entregadorRepository.findByLogin(usuario.getLogin()) == null)
                    throwTipoPerfilInvalido();
                break;
            case STUDENT:
                if (alunoRepository.findByLogin(usuario.getLogin()) == null)
                    throwTipoPerfilInvalido();
                break;
            default:
                throwTipoPerfilInvalido();
        }
        
    }

    @Override
    public EmailAvailableResponseDTO checkEmailAvailable(EmailAvailableDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(dto.email());

        if (usuario == null) {
            return new EmailAvailableResponseDTO(dto.email(), true);
        } else {
            return new EmailAvailableResponseDTO(dto.email(), false);
        }
    }

    @Override
    public EmailTakenClienteResponseDTO checkEmailTakenCliente(EmailAvailableDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(dto.email());

        Cliente cliente = null;
        if (usuario != null) {
            cliente = clienteRepository.findByLogin(dto.email());
        }

        return EmailTakenClienteResponseDTO.valueOf(dto.email(), usuario == null, cliente != null);
    }


    @Override
    public UsuarioTiposPerfilByEmailResponseDTO usuarioTiposPerfilByEmail(EmailAvailableDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(dto.email());

        if (usuario == null) {
            // return new UsuarioTiposPerfilByEmailResponseDTO(dto.email(),  false, false, false, false);
            return UsuarioTiposPerfilByEmailResponseDTO.valueOf(dto.email(), null, null, null, null);
        }

        Admin admin = adminRepository.findByLogin(dto.email());
        // TipoAdmin tipoAdmin = (admin != null) ? admin.getTipoAdmin() : null;
        Cliente cliente = clienteRepository.findByLogin(dto.email());
        Entregador entregador = entregadorRepository.findByLogin(dto.email());

        return UsuarioTiposPerfilByEmailResponseDTO.valueOf(
            dto.email(),
            (admin != null && admin.getTipoAdmin() == TipoAdmin.OWNER) ? admin.getId() : null,
            (admin != null && admin.getTipoAdmin() == TipoAdmin.EMPLOYEE) ? admin.getId() : null,
            (cliente != null) ? cliente.getId() : null,
            (entregador != null) ? entregador.getId() : null
        );
    }

    @Override
    public String login(AuthUsuarioDTO dto) {
        // String hashSenha = hashService.getHashSenha(dto.senha());
        String hashSenha = dto.senha(); // Assuming the password is already hashed in the DTO

        Usuario usuario = usuarioRepository.findByLoginAndSenha(dto.login(), hashSenha);
        if (usuario == null)
            throw new ValidationException("auth", "Login ou senha inválidos");
        
        validTipoPerfil(usuario, dto);

        String token = jwtService.generateJwt(usuario, TipoPerfil.valueOf(dto.idTipoPerfil()));

        return token;
    }
   
    @Override
    public AdminResponseDTO userInfoAdmin() {
        String login = jwt.getSubject();
        Admin admin = adminRepository.findByLogin(login);
        return AdminResponseDTO.valueOf(admin);
    }

    @Override
    public ClienteResponseDTO userInfoCliente() {
        String login = jwt.getSubject();
        Cliente cliente = clienteRepository.findByLogin(login);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public EntregadorResponseDTO userInfoEntregador() {
        String login = jwt.getSubject();
        Entregador entregador = entregadorRepository.findByLogin(login);
        return EntregadorResponseDTO.valueOf(entregador);
    }

    @Override
    public AlunoResponseDTO userInfoAluno(String token) {
        // String login = jwt.getSubject();
        String login = jwtService.getLoginFromToken(token);
        Aluno aluno = alunoRepository.findByLogin(login);

        // Buscar cursos que o aluno tem MatriculaCursoAluno
        List<Curso> cursos = cursoRepository.findByAlunoWithMatriculaCurso(aluno.getId());

        // Filtrar disciplinas dentro de cada curso
        List<CursoResponseDTO> cursosResponse = cursos.stream()
            .map(curso -> {
                List<DisciplinaResponseDTO> disciplinas = curso.getDisciplinas().stream()
                    .filter(disciplina -> disciplina.getMatriculasDisciplinaAluno().stream()
                        .anyMatch(matricula -> matricula.getAluno().getId().equals(aluno.getId()))) // Filtrar disciplinas com matrículas do aluno
                    .map(disciplina -> {
                        List<MatriculaDisciplinaAlunoResponseDTO> matriculas = disciplina.getMatriculasDisciplinaAluno().stream()
                            .filter(matricula -> matricula.getAluno().getId().equals(aluno.getId()))
                            .map(MatriculaDisciplinaAlunoResponseDTO::valueOf)
                            .toList();

                        return new DisciplinaResponseDTO(
                            disciplina.getId(),
                            disciplina.getPeriodoLetivo(),
                            disciplina.getCodigo(),
                            disciplina.getNome(),
                            disciplina.getCargaHoraria(),
                            disciplina.getCreditos(),
                            disciplina.getPeriodoCurso(),
                            matriculas
                        );
                    })
                    .toList();

                return new CursoResponseDTO(curso.getId(), curso.getNome(), curso.getCodigo(), disciplinas);
            })
            .toList();

        List<MatriculaDisciplinaAluno> matriculaDisciplinaAluno = matriculaDisciplinaAlunoRepository.findByAluno(aluno.getId());

        return AlunoResponseDTO.valueOf(aluno,  cursosResponse);

    }
    

    // @Override
    // public List<UsuarioResponseDTO> findAll() {
    //     return repository.listAll().stream()
    //         .map(e -> UsuarioResponseDTO.valueOf(e)).toList();
    // }


    // @Override
    // public UsuarioResponseDTO findByLogin(String login) {
    //     Usuario usuario = repository.findByLogin(login);
    //     if (usuario == null)
    //     throw new ValidationException("login", "Login inválido");

    //     return UsuarioResponseDTO.valueOf(usuario);
    // }


    // @Override
    // @Transactional
    // public void updateSenha(UsuarioUpdateSenhaDTO dto) {

    //     String login = jwt.getSubject();
    //     Usuario usuario = repository.findByLogin(login);
    //     if (usuario == null) 
    //         throw new ValidationException("login", "Login inválido");

    //     String hashSenhaAntiga = hashService.getHashSenha(dto.senhaAntiga());

    //     if (!hashSenhaAntiga.equals(usuario.getSenha()))
    //         throw new ValidationException("senhaAntiga", "A senha informada está incorreta");

    //     String hashSenhaNova = hashService.getHashSenha(dto.senhaNova());

    //     usuario.setSenha(hashSenhaNova);
    // }
    
}
