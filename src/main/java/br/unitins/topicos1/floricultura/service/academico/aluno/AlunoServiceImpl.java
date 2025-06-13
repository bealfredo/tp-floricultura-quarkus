package br.unitins.topicos1.floricultura.service.academico.aluno;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.floricultura.dto.PlantaResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.AlunoFastCreateDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.AlunoResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.AlunoUpdateDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.CursoResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.DisciplinaResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.MatriculaDisciplinaAlunoResponseDTO;
import br.unitins.topicos1.floricultura.dto.academico.aluno.RematriculaDTO;
import br.unitins.topicos1.floricultura.dto.academico.form.AlunoImageForm;
import br.unitins.topicos1.floricultura.form.PlantaImageForm;
import br.unitins.topicos1.floricultura.model.Planta;
import br.unitins.topicos1.floricultura.model.TipoPerfil;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.model.academico.Aluno;
import br.unitins.topicos1.floricultura.model.academico.Curso;
import br.unitins.topicos1.floricultura.model.academico.Disciplina;
import br.unitins.topicos1.floricultura.model.academico.MatriculaDisciplinaAluno;
import br.unitins.topicos1.floricultura.model.academico.StatusMatriculaDisciplina;
import br.unitins.topicos1.floricultura.repository.CidadeRepository;
import br.unitins.topicos1.floricultura.repository.EnderecoRepository;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import br.unitins.topicos1.floricultura.repository.academico.AlunoRepository;
import br.unitins.topicos1.floricultura.repository.academico.CursoRepository;
import br.unitins.topicos1.floricultura.repository.academico.DisciplinaRepository;
import br.unitins.topicos1.floricultura.repository.academico.MatriculaDisciplinaAlunoRepository;
import br.unitins.topicos1.floricultura.service.HashService;
import br.unitins.topicos1.floricultura.service.JwtService;
import br.unitins.topicos1.floricultura.service.PlantaFileService;
import br.unitins.topicos1.floricultura.validation.GeneralValidationException;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class AlunoServiceImpl implements AlunoService{

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    JsonWebToken jwt;


    @Inject
    AlunoRepository repository;

    @Inject
    MatriculaDisciplinaAlunoRepository matriculaDisciplinaAlunoRepository;

    @Inject
    DisciplinaRepository disciplinaRepository;

    @Inject
    CursoRepository cursoRepository;

    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    AlunoFileService alunoFileService;

    // private void validUpdate(AlunoUpdateDTO dto, Aluno obj2Update) {
    //     // for (EnderecoDTO endereco : dto.listaEndereco()) {
    //     //     if (endereco != null) {
    //     //         Cidade cidade = cidadeRepository.findById(endereco.cidade());
    //     //         if (cidade == null) {
    //     //             throw new ValidationException("cidade", "Cidade não encontrada.");
    //     //         }
    //     //     }
    //     // }

    //     if (!dto.cpf().equals(obj2Update.getUsuario().getCpf())) {
    //         Usuario usuario = usuarioRepository.findByCpf(dto.cpf());
    //         if (usuario != null) {
    //             throw new ValidationException("cpf", "CPF já cadastrado.");
    //         }
    //     }
    // }


    @Override
    @Transactional
    public String insert(@Valid AlunoFastCreateDTO dto) {

        Usuario usuario = usuarioRepository.findByLogin(dto.email());
        if (usuario != null) {
            throw new ValidationException("email", "Email já cadastrado.");
        }        
        Usuario usuario2 = usuarioRepository.findByCpf(dto.cpf());
        if (usuario2 != null) {
            throw new ValidationException("cpf", "CPF já cadastrado.");
        }


        usuario = new Usuario();
        usuario.setNome(dto.primeiroNome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setLogin(dto.email());
        usuario.setCpf(dto.cpf());
        // usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuario.setSenha(dto.senha());
        usuario.setDataNascimento(dto.dataNascimento());

        
        
        usuarioRepository.persist(usuario);
        
        Aluno aluno = new Aluno();
        aluno.setMatricula(dto.matricula());
        aluno.setUsuario(usuario);
        aluno.setPeriodoAtual(dto.periodoAtual());
        aluno.setMatriculaPendente(dto.matriculaPendente());
        aluno.setImagemPrincipal(null);
        aluno.setImagens(new String[0]);

        repository.persist(aluno);

        String token = jwtService.generateJwt(usuario, TipoPerfil.CUSTOMER);

        return token;
    }

    @Override
    @Transactional
    public AlunoResponseDTO update(@Valid AlunoUpdateDTO dto, Long id) {
        Aluno aluno = repository.findById(id);
        if (aluno == null) {
            throw new NotFoundException();
        }

        // validUpdate(dto, aluno);

        aluno.setCorRaca(dto.corRaca());
        aluno.setUf(dto.uf());
        aluno.setCidade(dto.cidade());
        aluno.setBairro(dto.bairro());
        aluno.setCep(dto.cep());
        aluno.setLogradouro(dto.logradouro());
        aluno.setNumero(dto.numero());
        aluno.setComplemento(dto.complemento());
        aluno.setEmailPessoal(dto.emailPessoal());
        aluno.setTelefoneCelular1(dto.telefoneCelular1());
        aluno.setTelefoneCelular2(dto.telefoneCelular2());
        aluno.setTelefoneFixo(dto.telefoneFixo());

        // Buscar cursos que o aluno tem MatriculaCursoAluno
        List<Curso> cursos = cursoRepository.findByAlunoWithMatriculaCurso(id);

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

        List<MatriculaDisciplinaAluno> matriculaDisciplinaAluno = matriculaDisciplinaAlunoRepository.findByAluno(id);

        return AlunoResponseDTO.valueOf(aluno,  cursosResponse);

    }

    @Override
    @Transactional
    public AlunoResponseDTO selfUpdate(@Valid AlunoUpdateDTO dto) {
        String login = jwt.getSubject();
        Aluno aluno = repository.findByLogin(login);

        if (aluno == null) {
            throw new NotFoundException();
        }

        AlunoResponseDTO alunoUpdated =  this.update(dto, aluno.getId());

        return alunoUpdated;
    }


    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException();
        }
    }

    @Override
public AlunoResponseDTO findById(Long id) {
    Aluno aluno = repository.findById(id);

    if (aluno == null) {
        throw new NotFoundException();
    }

    // Buscar cursos que o aluno tem MatriculaCursoAluno
    List<Curso> cursos = cursoRepository.findByAlunoWithMatriculaCurso(id);

    // Filtrar disciplinas dentro de cada curso
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

    List<MatriculaDisciplinaAluno> matriculaDisciplinaAluno = matriculaDisciplinaAlunoRepository.findByAluno(id);

    return AlunoResponseDTO.valueOf(aluno,  cursosResponse);
}

    @Override
    public AlunoResponseDTO findByToken() {
        // Cliente cliente = repository.findById(id);

        // if (cliente == null) {
        //     throw new NotFoundException();
        // }

        // return ClienteResponseDTO.valueOf(cliente);

        String login = jwt.getSubject();
        Aluno aluno = repository.findByLogin(login);

        if (aluno == null) {
            throw new NotFoundException();
        }

        // Buscar cursos que o aluno tem MatriculaCursoAluno
        List<Curso> cursos = cursoRepository.findByAlunoWithMatriculaCurso(aluno.getId());

        // Filtrar disciplinas dentro de cada curso
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

        return AlunoResponseDTO.valueOf(aluno, cursosResponse);
    }

    @Override
    public List<AlunoResponseDTO> findByAll(int page, int pageSize) {
        
        List<Aluno> list = repository
                                .findAll()
                                .page(page, pageSize)
                                .list();

        return list.stream()
            .map(e -> {
                // List<Curso> cursos = cursoRepository.findByAluno(e.getId());
                // List<MatriculaDisciplinaAluno> matriculas = matriculaDisciplinaAlunoRepository.findByAluno(e.getId());
                // return AlunoResponseDTO.valueOf(e, matriculas, cursos);

                // Buscar cursos que o aluno tem MatriculaCursoAluno
                List<Curso> cursos = cursoRepository.findByAlunoWithMatriculaCurso(e.getId());

                // Filtrar disciplinas dentro de cada curso
                // Filtrar disciplinas dentro de cada curso
                List<CursoResponseDTO> cursosResponse = cursos.stream()
                    .map(curso -> {
                        List<DisciplinaResponseDTO> disciplinas = curso.getDisciplinas().stream()
                            .filter(disciplina -> disciplina.getMatriculasDisciplinaAluno().stream()
                                .anyMatch(matricula -> matricula.getAluno().getId().equals(e.getId())))
                            .map(disciplina -> {
                                List<MatriculaDisciplinaAlunoResponseDTO> matriculas = disciplina.getMatriculasDisciplinaAluno().stream()
                                    .filter(matricula -> matricula.getAluno().getId().equals(e.getId()))
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

                List<MatriculaDisciplinaAluno> matriculaDisciplinaAluno = matriculaDisciplinaAlunoRepository.findByAluno(e.getId());

                return AlunoResponseDTO.valueOf(e, cursosResponse);

            }).collect(Collectors.toList());

    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public AlunoResponseDTO rematricula(@Valid RematriculaDTO dto, Long id) {
        Aluno aluno = repository.findById(id);
        if (aluno == null) {
            throw new NotFoundException("Aluno não encontrado.");
        }

        // // Atualizar informações do aluno
        // validUpdate(dto, aluno);
        // aluno.getUsuario().setNome(dto.nome());
        // aluno.getUsuario().setSobrenome(dto.sobrenome());
        // aluno.getUsuario().setCpf(dto.cpf());
        // aluno.getUsuario().setDataNascimento(dto.dataNascimento());
        // repository.persist(aluno);

        // // Remover matrículas antigas
        // matriculaDisciplinaAlunoRepository.deleteByAlunoId(aluno.getId());

        // Criar novas matrículas com base nos IDs das disciplinas
        List<MatriculaDisciplinaAluno> novasMatriculas = dto.disciplinasId().stream()
            .map(disciplinaId -> {
                Disciplina disciplina = disciplinaRepository.findById(disciplinaId);
                if (disciplina == null) {
                    throw new NotFoundException("Disciplina com ID " + disciplinaId + " não encontrada.");
                }

                MatriculaDisciplinaAluno matricula = new MatriculaDisciplinaAluno();
                matricula.setAluno(aluno);
                matricula.setDisciplina(disciplina);
                matricula.setStatusMatriculaDisciplina("Matriculado"); // Status inicial
                return matricula;
            })
            .toList();

        matriculaDisciplinaAlunoRepository.persist(novasMatriculas);

        // Retornar o aluno atualizado com as novas matrículas
        List<Curso> cursos = cursoRepository.findByAlunoWithMatriculaCurso(aluno.getId());
        List<CursoResponseDTO> cursosResponse = cursos.stream()
            .map(curso -> {
                List<DisciplinaResponseDTO> disciplinas = curso.getDisciplinas().stream()
                    .filter(disciplina -> disciplina.getMatriculasDisciplinaAluno().stream()
                        .anyMatch(matricula -> matricula.getAluno().getId().equals(aluno.getId())))
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

        // update aluno information
        aluno.setPeriodoAtual(aluno.getPeriodoAtual() + 1);
        aluno.setMatriculaPendente(false);

        return AlunoResponseDTO.valueOf(aluno, cursosResponse);
    }








    @Override
    @Transactional
    public AlunoResponseDTO adicionarImagem(AlunoImageForm form, Long id) {
        Aluno aluno = repository.findById(id);
        if (aluno == null) {
            throw new NotFoundException();
        }

        String nomeImagem = "";

        try {
            nomeImagem = alunoFileService.salvar(aluno.getId(), form.getImagem());
        } catch (Exception e) {
            throw new GeneralValidationException("Imagem aluno", "Erro ao adicionar a imagem: " + e.getMessage());
        }

        System.out.println("Nome da imagem salva: " + nomeImagem);
        List<String> imagens;
        if (aluno.getImagens() != null) {
            imagens = new ArrayList<>(Arrays.asList(aluno.getImagens()));
        } else {
            imagens = new ArrayList<>();
        }
        imagens.add(nomeImagem);

        aluno.setImagens(imagens.toArray(new String[0]));

        if (aluno.getImagemPrincipal() == null && imagens.size() == 1) {
            aluno.setImagemPrincipal(nomeImagem);
        }

        // Buscar cursos que o aluno tem MatriculaCursoAluno
        List<Curso> cursos = cursoRepository.findByAlunoWithMatriculaCurso(aluno.getId());


        // // Filtrar disciplinas dentro de cada curso
        //         List<CursoResponseDTO> cursosResponse = cursos.stream()
        //             .map(curso -> {
        //                 List<DisciplinaResponseDTO> disciplinas = curso.getDisciplinas().stream()
        //                     .filter(disciplina -> disciplina.getMatriculasDisciplinaAluno().stream()
        //                         .anyMatch(matricula -> matricula.getAluno().getId().equals(aluno.getId()))) // Filtrar disciplinas com matrículas do aluno
        //                     .map(disciplina -> {
        //                         List<MatriculaDisciplinaAlunoResponseDTO> matriculas = disciplina.getMatriculasDisciplinaAluno().stream()
        //                             .filter(matricula -> matricula.getAluno().getId().equals(aluno.getId()))
        //                             .map(MatriculaDisciplinaAlunoResponseDTO::valueOf)
        //                             .toList();

        //                         return new DisciplinaResponseDTO(disciplina.getId(), disciplina.getNome(), disciplina.getCodigo(), matriculas);
        //                     })
        //                     .toList();

        //                 return new CursoResponseDTO(curso.getId(), curso.getNome(), curso.getCodigo(), disciplinas);
        //             })
        //             .toList();

        List<CursoResponseDTO> cursosResponse = null;
        return AlunoResponseDTO.valueOf(aluno, cursosResponse);
    }

    @Override
    public File downloadImagem(String nomeImagem, Long id) {
        Aluno aluno = repository.findById(id);
        if (aluno == null) {
            throw new NotFoundException();
        }

        // String login = jwt.getSubject();
        // UsuarioResponseDTO usuarioResponseDTO = usuarioService.fin(id);

        // if (usuarioResponseDTO.tipoUsuario() == TipoUsuario.CLIENTE) {
            // if (!(planta.getStatusProduto() == StatusProduto.ATIVO)) {
            //     throw new GeneralValidationException("Imagem produto", "O produto não está ativo");
            // }
        // }

        if (aluno.getImagens() == null || aluno.getImagens().length == 0) {
            throw new GeneralValidationException("Imagem aluno", "O aluno não possui imagem");
        }

        for (String imagem : aluno.getImagens()) {
            if (imagem.equals(nomeImagem)) {
                return alunoFileService.obter(aluno.getId(), nomeImagem);
            }
        }

        throw new GeneralValidationException("Imagem aluno", "A imagem não foi encontrada");
    }

    @Override
    @Transactional
    public void deleteImagem(String nomeImagem, Long id) {
        Aluno aluno = repository.findById(id);
        if (aluno == null) {
            throw new NotFoundException();
        }

        List<String> imagens = new ArrayList<>(Arrays.asList(aluno.getImagens()));
        Boolean imagemEncontrada = false;
        for (String imagem : imagens) {
            if (imagem.equals(nomeImagem)) {
                imagemEncontrada = true;
                break;
            }
        }
        if (!imagemEncontrada) {
            throw new GeneralValidationException("Imagem aluno", "A imagem não foi encontrada");
        }

        alunoFileService.apagar(aluno.getId(), nomeImagem);
        imagens.remove(nomeImagem);
        aluno.setImagens(imagens.toArray(new String[0]));
        
        if (aluno.getImagemPrincipal().equals(nomeImagem)) {
            aluno.setImagemPrincipal(null);
        }
    }

    @Override
    @Transactional
    public void definirImagemPrincipal(String nomeImagem, Long id) {
        Aluno aluno = repository.findById(id);
        if (aluno == null) {
            throw new NotFoundException();
        }

        List<String> imagens = new ArrayList<>(Arrays.asList(aluno.getImagens()));
        Boolean imagemEncontrada = false;
        for (String imagem : imagens) {
            if (imagem.equals(nomeImagem)) {
                imagemEncontrada = true;
                break;
            }
        }
        if (!imagemEncontrada) {
            throw new GeneralValidationException("Imagem aluno", "A imagem não foi encontrada");
        }

        aluno.setImagemPrincipal(nomeImagem);
    }

    
    @Override
    public List<DisciplinaResponseDTO> findDisciplinasByCurso(Long cursoId) {
      Curso curso = cursoRepository.findById(cursoId);
      if (curso == null) {
        throw new NotFoundException("Curso não encontrado");
      }
      
      return curso.getDisciplinas().stream()
        .map(disciplina -> {
          // Creating empty list for matriculas since we're not filtering by student
          List<MatriculaDisciplinaAlunoResponseDTO> matriculas = new ArrayList<>();
          
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
    }


    // @Override
    // @Transactional
    // public String insertExistingUser(ClienteExistingUserDTO dto) {
    //     Cliente cliente = repository.findByLogin(dto.email());
    //     if (cliente != null) {
    //         throw new ValidationException("login", "Cliente já cadastrado.");
    //     }

    //     String hashSenha = hashService.getHashSenha(dto.passwordExisting());

    //     Usuario usuario = usuarioRepository.findByLoginAndSenha(dto.email(), hashSenha);
    //     if (usuario == null) {
    //         throw new ValidationException("login", "Login ou senha inválidos");
    //     }

    //     cliente = new Cliente();
    //     cliente.setUsuario(usuario);
    //     cliente.setCarrinho(null);
    //     cliente.setListaEndereco(new ArrayList<Endereco>());

    //     repository.persist(cliente);

    //     String token = jwtService.generateJwt(usuario, TipoPerfil.CUSTOMER);

    //     return token;
    // }


    // @Override
    // public List<EnderecoDTO> getListaEndereco() {
    //     String login = jwt.getSubject();
    //     Cliente cliente = repository.findByLogin(login);

    //     if (cliente == null) {
    //         throw new NotFoundException();
    //     }

    //     return cliente.getListaEndereco().stream()
    //         .map(e -> EnderecoDTO.valueOf(e)).collect(Collectors.toList());
    // }

    // return list.stream()
    // .map(e -> ClienteResponseDTO.valueOf(e)).collect(Collectors.toList());


}
