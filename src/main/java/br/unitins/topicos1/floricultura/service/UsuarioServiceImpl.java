package br.unitins.topicos1.floricultura.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.floricultura.dto.EnderecoDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioResponseDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioUpdateInfoDTO;
import br.unitins.topicos1.floricultura.dto.UsuarioUpdateSenhaDTO;
import br.unitins.topicos1.floricultura.dto.VendaResponseDTO;
import br.unitins.topicos1.floricultura.model.Cidade;
import br.unitins.topicos1.floricultura.model.Endereco;
import br.unitins.topicos1.floricultura.model.Telefone;
import br.unitins.topicos1.floricultura.model.TipoUsuario;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.repository.CidadeRepository;
import br.unitins.topicos1.floricultura.repository.TelefoneRepository;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    HashService hashService;

    @Inject
    UsuarioRepository repository;

    @Inject
    TelefoneRepository repositoryTelefone;

    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    VendaService vendaService;

    @Inject
    JwtService jwtService;

    @Inject
    JsonWebToken jwt;

    @Override
    @Transactional
    public UsuarioResponseDTO insert(@Valid UsuarioDTO dto) {

       if (repository.findByLogin(dto.login()) != null) {
            throw new ValidationException("login", "Login já existe.");
        }

        if (repository.findByCpf(dto.cpf()) != null) {
            throw new ValidationException("cpf", "Cpf já cadastrado.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.nome());
        novoUsuario.setSobreNome(dto.sobreNome());
        novoUsuario.setLogin(dto.login());
        novoUsuario.setCpf(dto.cpf());
        novoUsuario.setSenha(hashService.getHashSenha(dto.senha()));
        novoUsuario.setDataNascimento(dto.dataNascimento());
        Telefone telefone = repositoryTelefone.findById(dto.telefone());
        try {
            novoUsuario.setTelefone(telefone);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        if (dto.listaEndereco() != null && 
                    !dto.listaEndereco().isEmpty()){
            novoUsuario.setListaEndereco(new ArrayList<Endereco>());
            for (EnderecoDTO e : dto.listaEndereco()) {
                Endereco endereco = new Endereco();
                endereco.setCodigo(e.codigo());
                endereco.setRua(e.rua());
                endereco.setBairro(e.bairro());
                endereco.setNumeroLote(e.numeroLote());
                endereco.setComplemento(e.complemento());

                Cidade cidade = cidadeRepository.findById(e.cidade());
                if (cidade == null) {
                    throw new ValidationException("cidade", "A cidade de um dos endereços é inválida.");
                }

                endereco.setCidade(cidade);
            }
        }

        novoUsuario.setTipoUsuario(TipoUsuario.CLIENTE);

        repository.persist(novoUsuario);

        return UsuarioResponseDTO.valueOf(novoUsuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(UsuarioUpdateInfoDTO dto, Long id) {
        Usuario usuario = repository.findById(id);
        usuario.setNome(dto.nome());
        usuario.setSobreNome(dto.sobreNome());
        usuario.setCpf(dto.cpf());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setTelefone(dto.telefone());
        
        if (dto.listaEndereco() != null && 
                    !dto.listaEndereco().isEmpty()){
            usuario.setListaEndereco(new ArrayList<Endereco>());
            for (EnderecoDTO e : dto.listaEndereco()) {
                Endereco endereco = new Endereco();
                endereco.setCodigo(e.codigo());
                endereco.setRua(e.rua());
                endereco.setBairro(e.bairro());
                endereco.setNumeroLote(e.numeroLote());
                endereco.setComplemento(e.complemento());

                Cidade cidade = cidadeRepository.findById(e.cidade());
                if (cidade == null) {
                    throw new ValidationException("cidade", "A cidade de um dos endereços é inválida.");
                }

                endereco.setCidade(cidade);
            }
        }
        
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
    }

    @Override
    public List<UsuarioResponseDTO> findAll() {
        return repository.listAll().stream()
            .map(e -> UsuarioResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        return UsuarioResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<UsuarioResponseDTO> findByNome(String nome) {
        return null;
    }

    @Override
    public UsuarioResponseDTO findByLoginAndSenha(String login, String senha) {
        Usuario usuario = repository.findByLoginAndSenha(login, senha);
        if (usuario == null)
        throw new ValidationException("login e senha", "Login ou senha inválidos");

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public UsuarioResponseDTO findByLogin(String login) {
        Usuario usuario = repository.findByLogin(login);
        if (usuario == null)
        throw new ValidationException("login", "Login inválido");

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public UsuarioResponseDTO userInfo() {
        String login = jwt.getSubject();

        Usuario usuario = repository.findByLogin(login);

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    public List<VendaResponseDTO> minhasCompras() {
            String login = jwt.getSubject();

            Usuario usuario = repository.findByLogin(login);

            List<VendaResponseDTO> vendas = null;

            try {
                vendas = vendaService.findByUsuario(usuario.getId());
            } catch (Exception e) {
            }

            return vendas;
        }


    @Override
    @Transactional
    public void updateSenha(UsuarioUpdateSenhaDTO dto) {

        String login = jwt.getSubject();
        Usuario usuario = repository.findByLogin(login);
        if (usuario == null) 
            throw new ValidationException("login", "Login inválido");

        String hashSenhaAntiga = hashService.getHashSenha(dto.senhaAntiga());

        if (!hashSenhaAntiga.equals(usuario.getSenha()))
            throw new ValidationException("senhaAntiga", "A senha informada está incorreta");

        String hashSenhaNova = hashService.getHashSenha(dto.senhaNova());

        usuario.setSenha(hashSenhaNova);
    }
    
}
