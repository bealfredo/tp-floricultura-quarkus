package br.unitins.topicos1.floricultura.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.floricultura.dto.ClienteExistingUserDTO;
import br.unitins.topicos1.floricultura.dto.ClienteFastCreateDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.ClienteUpdateDTO;
import br.unitins.topicos1.floricultura.dto.EnderecoDTO;
import br.unitins.topicos1.floricultura.model.Cidade;
import br.unitins.topicos1.floricultura.model.Cliente;
import br.unitins.topicos1.floricultura.model.Endereco;
import br.unitins.topicos1.floricultura.model.Telefone;
import br.unitins.topicos1.floricultura.model.TipoPerfil;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.repository.CidadeRepository;
import br.unitins.topicos1.floricultura.repository.ClienteRepository;
import br.unitins.topicos1.floricultura.repository.EnderecoRepository;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService{

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;


    @Inject
    ClienteRepository repository;

    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    private void validUpdate(ClienteUpdateDTO dto, Cliente obj2Update) {
        for (EnderecoDTO endereco : dto.listaEndereco()) {
            if (endereco != null) {
                Cidade cidade = cidadeRepository.findById(endereco.cidade());
                if (cidade == null) {
                    throw new ValidationException("cidade", "Cidade não encontrada.");
                }
            }
        }

        if (!dto.cpf().equals(obj2Update.getUsuario().getCpf())) {
            Usuario usuario = usuarioRepository.findByCpf(dto.cpf());
            if (usuario != null) {
                throw new ValidationException("cpf", "CPF já cadastrado.");
            }
        }
    }


    @Override
    @Transactional
    public ClienteResponseDTO insert(@Valid ClienteFastCreateDTO dto) {

        Usuario usuario = usuarioRepository.findByLogin(dto.email());
        if (usuario != null) {
            throw new ValidationException("email", "Email já cadastrado.");
        }

        usuario = new Usuario();
        usuario.setNome(dto.primeiroNome());
        usuario.setLogin(dto.email());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));

        usuarioRepository.persist(usuario);

        Cliente cliente = new Cliente();
        cliente.setCarrinho(dto.carrinho());
        cliente.setUsuario(usuario);
        cliente.setListaEndereco(new ArrayList<Endereco>());

        repository.persist(cliente);

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(@Valid ClienteUpdateDTO dto, Long id) {
        Cliente cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException();
        }

        validUpdate(dto, cliente);

        // remove todos os endereços existentes
        for (Endereco endereco : cliente.getListaEndereco()) {
            enderecoRepository.deleteById(endereco.getId());
        }

        if (dto.telefone() != null) {
            Telefone telefone = new Telefone();
            telefone.setDdd(dto.telefone().ddd());
            telefone.setNumero(dto.telefone().numero());
            cliente.getUsuario().setTelefone(telefone);
        } else {
            cliente.getUsuario().setTelefone(null);
        }

        List<Endereco> listaEndereco = dto.listaEndereco().stream()
            .map(e -> {
                Endereco endereco = new Endereco();
                endereco.setNome(e.nome());
                endereco.setRua(e.rua());
                endereco.setCidade(cidadeRepository.findById(e.cidade()));
                endereco.setBairro(e.bairro());
                endereco.setCep(e.cep());
                endereco.setComplemento(e.complemento());
                endereco.setNumeroLote(e.numeroLote());
                return endereco;
            }).collect(Collectors.toList());
            
        // cliente.setCarrinho(dto.carrinho());
        cliente.setListaEndereco(listaEndereco);

        cliente.getUsuario().setNome(dto.nome());
        cliente.getUsuario().setSobrenome(dto.sobrenome());
        cliente.getUsuario().setCpf(dto.cpf());
        cliente.getUsuario().setDataNascimento(dto.dataNascimento());


        repository.persist(cliente);

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException();
        }
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = repository.findById(id);

        if (cliente == null) {
            throw new NotFoundException();
        }

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public List<ClienteResponseDTO> findByAll(int page, int pageSize) {
        List<Cliente> list = repository
                                .findAll()
                                .page(page, pageSize)
                                .list();

        return list.stream()
            .map(e -> ClienteResponseDTO.valueOf(e)).collect(Collectors.toList());

    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public String insertExistingUser(ClienteExistingUserDTO dto) {
        Cliente cliente = repository.findByLogin(dto.email());
        if (cliente != null) {
            throw new ValidationException("login", "Cliente já cadastrado.");
        }

        String hashSenha = hashService.getHashSenha(dto.passwordExisting());

        Usuario usuario = usuarioRepository.findByLoginAndSenha(dto.email(), hashSenha);
        if (usuario == null) {
            throw new ValidationException("login", "Login ou senha inválidos");
        }

        cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setCarrinho(null);
        cliente.setListaEndereco(new ArrayList<Endereco>());

        repository.persist(cliente);

        String token = jwtService.generateJwt(usuario, TipoPerfil.CUSTOMER);

        return token;
    }

}
