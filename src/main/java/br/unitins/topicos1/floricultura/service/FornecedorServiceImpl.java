package br.unitins.topicos1.floricultura.service;

import java.util.List;
import br.unitins.topicos1.floricultura.dto.FornecedorDTO;
import br.unitins.topicos1.floricultura.dto.FornecedorResponseDTO;
import br.unitins.topicos1.floricultura.model.Fornecedor;
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

  @Override
  @Transactional
  public FornecedorResponseDTO insert(@Valid FornecedorDTO dto) {

    List<Fornecedor> fornecedores = repository.findByCnpj(dto.cnpj());
    if (!dto.cnpj().equals("")) {
      if (!fornecedores.isEmpty()) {
        throw new ValidationException("cnpj", "CNPJ já cadastrado.");
      }
    }


    Fornecedor novoFornecedor = new Fornecedor();

    novoFornecedor.setNome(dto.nome());
    novoFornecedor.setEmail(dto.email());
    novoFornecedor.setTelefone(dto.telefone());
    novoFornecedor.setCnpj(dto.cnpj().equals("") ? null : dto.cnpj());

    repository.persist(novoFornecedor);
    
    return FornecedorResponseDTO.valueOf(novoFornecedor);
  }

  @Override
  @Transactional
  public FornecedorResponseDTO update(FornecedorDTO dto, Long id) {
    Fornecedor fornecedor = repository.findById(id);
    if (fornecedor != null) {
      fornecedor.setNome(dto.nome());
      fornecedor.setEmail(dto.email());
      fornecedor.setTelefone(dto.telefone());
      fornecedor.setCnpj(dto.cnpj());
    } else {
      throw new NotFoundException();
    }

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
}
