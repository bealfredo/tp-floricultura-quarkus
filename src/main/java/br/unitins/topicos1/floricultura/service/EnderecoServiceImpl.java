package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.EnderecoDTO;
import br.unitins.topicos1.floricultura.dto.EnderecoResponseDTO;
import br.unitins.topicos1.floricultura.model.Cidade;
import br.unitins.topicos1.floricultura.model.Endereco;
import br.unitins.topicos1.floricultura.repository.CidadeRepository;
import br.unitins.topicos1.floricultura.repository.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

  @Inject
  EnderecoRepository repository;

  @Inject
  CidadeRepository repositoryCidade;

  @Override
  @Transactional
  public EnderecoResponseDTO insert(EnderecoDTO dto) throws Exception {
    Endereco novoEndereco = new Endereco();
    novoEndereco.setNome(dto.nome());
    novoEndereco.setCep(dto.cep());
    novoEndereco.setCodigo(dto.codigo());
    novoEndereco.setRua(dto.rua());
    novoEndereco.setBairro(dto.bairro());
    novoEndereco.setNumeroLote(dto.numeroLote());
    novoEndereco.setComplemento(dto.complemento());
    Cidade cidade = repositoryCidade.findById(dto.cidade());

    if (cidade == null) {
      throw new Exception("A cidade informado não foi encontrada");
    }

    novoEndereco.setCidade(cidade);

    repository.persist(novoEndereco);
    
    return EnderecoResponseDTO.valueOf(novoEndereco);
  }

  @Override
  @Transactional
  public EnderecoResponseDTO update(EnderecoDTO dto, Long id) throws Exception {
    Endereco Endereco = repository.findById(id);

    Cidade cidade = repositoryCidade.findById(dto.cidade());

    if (cidade == null) {
        throw new Exception("A cidade informado não foi encontrada");
    }
    if (Endereco != null) {
        Endereco.setNome(dto.nome());
        Endereco.setCep(dto.cep());
        Endereco.setCodigo(dto.codigo());
        Endereco.setRua(dto.rua());
        Endereco.setBairro(dto.bairro());
        Endereco.setNumeroLote(dto.numeroLote());
        Endereco.setComplemento(dto.complemento());
    } else {
      throw new NotFoundException();
    }

    return EnderecoResponseDTO.valueOf(Endereco);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!repository.deleteById(id)) {
      throw new NotFoundException();
    }
  }

  @Override
  public List<EnderecoResponseDTO> findAll() {
    return repository.listAll()
      .stream()
      .map(e -> EnderecoResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public EnderecoResponseDTO findById(Long id) {
    Endereco Endereco = repository.findById(id);

    if (Endereco == null) {
      throw new NotFoundException();
    }

    return EnderecoResponseDTO.valueOf(Endereco);
  }

  @Override
  public List<EnderecoResponseDTO> findByNome(String nome) {
    return repository.findByNome(nome)
      .stream()
      .map(e -> EnderecoResponseDTO.valueOf(e))
      .toList();
  }
  
  @Override
  public List<EnderecoResponseDTO> findByCep(String cep) {
    return repository.findByCep(cep)
      .stream()
      .map(e -> EnderecoResponseDTO.valueOf(e))
      .toList();
  }
}
