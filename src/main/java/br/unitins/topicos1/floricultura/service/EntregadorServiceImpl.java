package br.unitins.topicos1.floricultura.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.floricultura.dto.EmailAvailableDTO;
import br.unitins.topicos1.floricultura.dto.EmailAvailableResponseDTO;
import br.unitins.topicos1.floricultura.dto.EntregadorCreateDTO;
import br.unitins.topicos1.floricultura.dto.EntregadorResponseDTO;
import br.unitins.topicos1.floricultura.dto.EntregadorUpdateDTO;
import br.unitins.topicos1.floricultura.model.Entregador;
import br.unitins.topicos1.floricultura.model.Telefone;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.repository.CidadeRepository;
import br.unitins.topicos1.floricultura.repository.EnderecoRepository;
import br.unitins.topicos1.floricultura.repository.EntregadorRepository;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EntregadorServiceImpl implements EntregadorService{

    @Inject
    HashService hashService;

    @Inject
    EntregadorRepository repository;

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

    private void valid(String newCpf, String oldCpf) {
        if (newCpf != null && oldCpf != null && !newCpf.equals(oldCpf)) {
            Usuario usuario = usuarioRepository.findByCpf(newCpf);
            if (usuario != null) {
                throw new ValidationException("cpf", "CPF já cadastrado.");
            }
        }

        if (newCpf != null && oldCpf == null) {
            Usuario usuario = usuarioRepository.findByCpf(newCpf);
            if (usuario != null) {
                throw new ValidationException("cpf", "CPF já cadastrado.");
            }
        }
    }


    @Override
    @Transactional
    public EntregadorResponseDTO insert(@Valid EntregadorCreateDTO dto) {

        Usuario usuario = usuarioRepository.findByLogin(dto.email());
        if (usuario != null) {
            throw new ValidationException("email", "Email já cadastrado.");
        }

        valid( dto.cpf(), null);
        
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.telefone().ddd());
        telefone.setNumero(dto.telefone().numero());

        usuario = new Usuario();
        usuario.setLogin(dto.email());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setCpf(dto.cpf());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setTelefone(telefone);

        usuarioRepository.persist(usuario);

        Entregador entregador = new Entregador();
        entregador.setUsuario(usuario);
        entregador.setCnh(dto.cnh());
        entregador.setCnpj(dto.cnpj());

        repository.persist(entregador);

        return EntregadorResponseDTO.valueOf(entregador);
    }

    @Override
    @Transactional
    public EntregadorResponseDTO update(@Valid EntregadorUpdateDTO dto, Long id) {
        Entregador entregador = repository.findById(id);
        if (entregador == null) {
            throw new NotFoundException();
        }

        valid(dto.cpf(), entregador.getUsuario().getCpf());

        Telefone telefone = new Telefone();
        telefone.setDdd(dto.telefone().ddd());
        telefone.setNumero(dto.telefone().numero());
            
        entregador.getUsuario().setNome(dto.nome());
        entregador.getUsuario().setSobrenome(dto.sobrenome());
        entregador.getUsuario().setCpf(dto.cpf());
        entregador.getUsuario().setDataNascimento(dto.dataNascimento());
        entregador.getUsuario().setTelefone(telefone);

        repository.persist(entregador);

        return EntregadorResponseDTO.valueOf(entregador);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException();
        }
    }

    @Override
    public EntregadorResponseDTO findById(Long id) {
        Entregador entregador = repository.findById(id);

        if (entregador == null) {
            throw new NotFoundException();
        }

        return EntregadorResponseDTO.valueOf(entregador);
    }

    @Override
    public List<EntregadorResponseDTO> findByAll(int page, int pageSize) {
        List<Entregador> list = repository
                                .findAll()
                                .page(page, pageSize)
                                .list();

        return list.stream()
            .map(e -> EntregadorResponseDTO.valueOf(e)).collect(Collectors.toList());

    }
}
