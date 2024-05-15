package br.unitins.topicos1.floricultura.service;

import java.util.List;
import br.unitins.topicos1.floricultura.dto.FornecedorDTO;
import br.unitins.topicos1.floricultura.dto.FornecedorResponseDTO;
import br.unitins.topicos1.floricultura.model.Fornecedor;
import br.unitins.topicos1.floricultura.model.Telefone;
import br.unitins.topicos1.floricultura.repository.FornecedorRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

// import org.jboss.logging.Logger;

@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService {
  // private static final Logger LOG = Logger.getLogger(AuthResource.class);

  @Inject
  FornecedorRepository repository;

  private void valid(FornecedorDTO dto) {
    List<Fornecedor> fornecedores = repository.findByCnpj(dto.cnpj());
    if (!dto.cnpj().equals("")) {
      if (!fornecedores.isEmpty()) {
        throw new ValidationException("cnpj", "CNPJ já cadastrado.");
      }
    }

    fornecedores = repository.findByEmail(dto.email());
    if (!fornecedores.isEmpty()) {
      throw new ValidationException("email", "Email já cadastrado.");
    }
  }

  @Override
  @Transactional
  public FornecedorResponseDTO insert(@Valid FornecedorDTO dto) {

    valid(dto);

    Fornecedor novoFornecedor = new Fornecedor();
    novoFornecedor.setNome(dto.nome());
    novoFornecedor.setEmail(dto.email());
    novoFornecedor.setCnpj(dto.cnpj().equals("") ? null : dto.cnpj());

    if (dto.telefone() != null) {
      Telefone telefone = new Telefone();
      telefone.setDdd(dto.telefone().ddd());
      telefone.setNumero(dto.telefone().numero());
      novoFornecedor.setTelefone(telefone);
    } else {
      novoFornecedor.setTelefone(null);
    }

    repository.persist(novoFornecedor);
    
    return FornecedorResponseDTO.valueOf(novoFornecedor);
  }

  @Override
  @Transactional
  public FornecedorResponseDTO update(@Valid FornecedorDTO dto, Long id) {
    Fornecedor fornecedor = repository.findById(id);

    if (fornecedor == null) {
      throw new NotFoundException();
    }

    valid(dto);

    Telefone telefone = new Telefone();
    telefone.setDdd(dto.telefone().ddd());
    telefone.setNumero(dto.telefone().numero());

    fornecedor.setNome(dto.nome());
    fornecedor.setEmail(dto.email());
    fornecedor.setTelefone(telefone);
    fornecedor.setCnpj(dto.cnpj());

    return FornecedorResponseDTO.valueOf(fornecedor);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!repository.deleteById(id)) {
      throw new NotFoundException();
    }
  }

  @Override
  public List<FornecedorResponseDTO> findAll() {
    return repository.listAll()
      .stream()
      .map(e -> FornecedorResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public FornecedorResponseDTO findById(Long id) {
    Fornecedor fornecedor = repository.findById(id);

    if (fornecedor == null) {
      throw new NotFoundException();
    }

    return FornecedorResponseDTO.valueOf(fornecedor);
  }

  @Override
  public List<FornecedorResponseDTO> findByNome(String nome) {
    return repository.findByNome(nome)
      .stream()
      .map(e -> FornecedorResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public List<FornecedorResponseDTO> findByCnpj(String cnpj) {
    return repository.findByCnpj(cnpj)
      .stream()
      .map(e -> FornecedorResponseDTO.valueOf(e))
      .toList();
  }

  @Override
  public List<FornecedorResponseDTO> findByEmail(String email) {
    return repository.findByEmail(email)
      .stream()
      .map(e -> FornecedorResponseDTO.valueOf(e))
      .toList();
  }
}
