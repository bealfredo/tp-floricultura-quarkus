package br.unitins.topicos1.floricultura.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.floricultura.dto.ClienteDTO;
import br.unitins.topicos1.floricultura.dto.ClienteResponseDTO;
import br.unitins.topicos1.floricultura.dto.EnderecoDTO;
import br.unitins.topicos1.floricultura.model.Cidade;
import br.unitins.topicos1.floricultura.model.Cliente;
import br.unitins.topicos1.floricultura.model.Endereco;
import br.unitins.topicos1.floricultura.repository.CidadeRepository;
import br.unitins.topicos1.floricultura.repository.ClienteRepository;
import br.unitins.topicos1.floricultura.repository.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService{

    @Inject
    ClienteRepository repository;

    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    private void valid(ClienteDTO dto, Cliente obj2Update) {

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
    public ClienteResponseDTO insert(@Valid ClienteDTO dto) {
        valid(dto, null);

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

        Cliente cliente = new Cliente();
        cliente.setCarrinho(dto.carrinho());
        cliente.setListaEndereco(listaEndereco);

        repository.persist(cliente);

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(@Valid ClienteDTO dto, Long id) {
        Cliente cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException();
        }

        valid(dto, cliente);

        // remove todos os endereços existentes
        for (Endereco endereco : cliente.getListaEndereco()) {
            enderecoRepository.deleteById(endereco.getId());
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

        cliente.setCarrinho(dto.carrinho());
        cliente.setListaEndereco(listaEndereco);

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
