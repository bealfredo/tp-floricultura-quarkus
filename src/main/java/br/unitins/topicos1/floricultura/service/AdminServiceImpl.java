package br.unitins.topicos1.floricultura.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.floricultura.dto.AdminCreateDTO;
import br.unitins.topicos1.floricultura.dto.AdminResponseDTO;
import br.unitins.topicos1.floricultura.dto.AdminSelfUpdateDTO;
import br.unitins.topicos1.floricultura.dto.AdminUpdateDTO;
import br.unitins.topicos1.floricultura.model.Admin;
import br.unitins.topicos1.floricultura.model.Telefone;
import br.unitins.topicos1.floricultura.model.TipoAdmin;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.repository.AdminRepository;
import br.unitins.topicos1.floricultura.repository.CidadeRepository;
import br.unitins.topicos1.floricultura.repository.EnderecoRepository;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class AdminServiceImpl implements AdminService{

    @Inject
    HashService hashService;

    @Inject
    AdminRepository repository;

    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    private void valid(Integer tipoAdminId, String newCpf, String oldCpf) {
        if (tipoAdminId != null) {
            TipoAdmin tipoAdmin = TipoAdmin.valueOf(tipoAdminId);
            if (tipoAdmin == null) {
                throw new ValidationException("tipoAdmin", "Tipo de administrador inválido.");
            }
        }

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
    public AdminResponseDTO insert(@Valid AdminCreateDTO dto) {

        Usuario usuario = usuarioRepository.findByLogin(dto.email());
        if (usuario != null) {
            throw new ValidationException("email", "Email já cadastrado.");
        }

        valid(dto.idTipoAdmin(), dto.cpf(), null);
        
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

        Admin admin = new Admin();
        admin.setUsuario(usuario);
        admin.setTipoAdmin(TipoAdmin.valueOf(dto.idTipoAdmin()));

        repository.persist(admin);

        return AdminResponseDTO.valueOf(admin);
    }

    @Override
    @Transactional
    public AdminResponseDTO update(@Valid AdminUpdateDTO dto, Long id) {
        Admin admin = repository.findById(id);
        if (admin == null) {
            throw new NotFoundException();
        }

        valid(dto.idTipoAdmin(), dto.cpf(), admin.getUsuario().getCpf());

        Telefone telefone = new Telefone();
        telefone.setDdd(dto.telefone().ddd());
        telefone.setNumero(dto.telefone().numero());
            
        admin.getUsuario().setNome(dto.nome());
        admin.getUsuario().setSobrenome(dto.sobrenome());
        admin.getUsuario().setCpf(dto.cpf());
        admin.getUsuario().setDataNascimento(dto.dataNascimento());
        admin.getUsuario().setTelefone(telefone);

        admin.setTipoAdmin(TipoAdmin.valueOf(dto.idTipoAdmin()));

        repository.persist(admin);

        return AdminResponseDTO.valueOf(admin);
    }

    @Override
    @Transactional
    public AdminResponseDTO selfUpdate(@Valid AdminSelfUpdateDTO  dto, Long id) {
        Admin admin = repository.findById(id);
        if (admin == null) {
            throw new NotFoundException();
        }

        valid(null, dto.cpf(), admin.getUsuario().getCpf());

        Telefone telefone = new Telefone();
        telefone.setDdd(dto.telefone().ddd());
        telefone.setNumero(dto.telefone().numero());
            
        admin.getUsuario().setNome(dto.nome());
        admin.getUsuario().setSobrenome(dto.sobrenome());
        admin.getUsuario().setCpf(dto.cpf());
        admin.getUsuario().setDataNascimento(dto.dataNascimento());
        admin.getUsuario().setTelefone(telefone);

        repository.persist(admin);

        return AdminResponseDTO.valueOf(admin);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException();
        }
    }

    @Override
    public AdminResponseDTO findById(Long id) {
        Admin admin = repository.findById(id);

        if (admin == null) {
            throw new NotFoundException();
        }

        return AdminResponseDTO.valueOf(admin);
    }

    @Override
    public List<AdminResponseDTO> findByAll(int page, int pageSize) {
        List<Admin> list = repository
                                .findAll()
                                .page(page, pageSize)
                                .list();

        return list.stream()
            .map(e -> AdminResponseDTO.valueOf(e)).collect(Collectors.toList());

    }
}
