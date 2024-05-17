package br.unitins.topicos1.floricultura.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.floricultura.dto.ClienteFastCreateDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.ClienteUpdateDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableResponseDTO;
import br.unitins.topicos1.floricultura.dto.EnderecoDTO;
import br.unitins.topicos1.floricultura.model.Cidade;
import br.unitins.topicos1.floricultura.model.Cliente;
import br.unitins.topicos1.floricultura.model.Endereco;
import br.unitins.topicos1.floricultura.model.Telefone;
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
    ClienteRepository repository;

    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public EmailAvailableResponseDTO checkEmailAvailable(@Valid EmailAvailableDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(dto.email());

        if (usuario == null) {
            return new EmailAvailableResponseDTO(dto.email(), true);
        } else {
            return new EmailAvailableResponseDTO(dto.email(), false);
        }
    }

    private void valid(ClienteUpdateDTO dto, Cliente obj2Update) {

        for (EnderecoDTO endereco : dto.listaEndereco()) {
            if (endereco != null) {
                Cidade cidade = cidadeRepository.findById(endereco.cidade());
                if (cidade == null) {
                    throw new ValidationException("cidade", "Cidade não encontrada.");
                }
            }
        }

        // if (obj2Update != null) {
        // } else {
        // }
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

        valid(dto, cliente);

        // remove todos os endereços existentes
        for (Endereco endereco : cliente.getListaEndereco()) {
            enderecoRepository.deleteById(endereco.getId());
        }

        Telefone telefone = new Telefone();
        telefone.setDdd(dto.telefone().ddd());
        telefone.setNumero(dto.telefone().numero());

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
            
        cliente.setCarrinho(dto.carrinho());
        cliente.setListaEndereco(listaEndereco);

        cliente.getUsuario().setNome(dto.nome());
        cliente.getUsuario().setSobrenome(dto.sobrenome());
        cliente.getUsuario().setCpf(dto.cpf());
        cliente.getUsuario().setDataNascimento(dto.dataNascimento());
        cliente.getUsuario().setTelefone(telefone);


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
        return ClienteResponseDTO.valueOf(repository.findById(id));
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
}
