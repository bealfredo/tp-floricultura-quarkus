package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.TelefoneDTO;
import br.unitins.topicos1.floricultura.dto.TelefoneResponseDTO;
import br.unitins.topicos1.floricultura.model.Telefone;
import br.unitins.topicos1.floricultura.repository.TelefoneRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService {

  @Inject
  TelefoneRepository repository;

  @Override
  @Transactional
  public TelefoneResponseDTO insert(TelefoneDTO dto) throws Exception {
    Telefone novoTelefone = new Telefone();
    novoTelefone.setDdd(dto.ddd());
    novoTelefone.setNumero(dto.numero());

    repository.persist(novoTelefone);
    
    return TelefoneResponseDTO.valueOf(novoTelefone);
  }

  @Override
  @Transactional
  public TelefoneResponseDTO update(TelefoneDTO dto, Long id) throws Exception {
    Telefone telefone = repository.findById(id);
    if (telefone != null) {
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
    } else {
      throw new NotFoundException();
    }
    return TelefoneResponseDTO.valueOf(telefone);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    if (!repository.deleteById(id)) {
      throw new NotFoundException();
    }
  }

    @Override
    public List<TelefoneResponseDTO> findAll() {
        return repository.listAll()
        .stream()
        .map(e -> TelefoneResponseDTO.valueOf(e))
        .toList();
    }

    @Override
    public TelefoneResponseDTO findById(Long id) {
        Telefone telefone = repository.findById(id);
        if (telefone == null) {
        throw new NotFoundException();
        }
        return TelefoneResponseDTO.valueOf(telefone);
    }
}


