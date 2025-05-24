package br.unitins.topicos1.floricultura.service;

import java.util.List;

import br.unitins.topicos1.floricultura.dto.CidadeDTO;
import br.unitins.topicos1.floricultura.dto.CidadeResponseDTO;
import br.unitins.topicos1.floricultura.model.Cidade;
import br.unitins.topicos1.floricultura.model.Estado;
import br.unitins.topicos1.floricultura.repository.CidadeRepository;
import br.unitins.topicos1.floricultura.repository.EstadoRepository;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CidadeServiceImpl implements CidadeService{
    
    @Inject
    CidadeRepository repository;
    @Inject
    EstadoRepository repositoryEstado;

    private void valid(CidadeDTO dto, Cidade obj2Update) {
        Estado estado = repositoryEstado.findById(dto.estado());
        if (estado == null) {
            throw new ValidationException("estado", "'O estado informado não foi encontrados");
        }

        if (obj2Update != null) {
            if (!dto.nome().equals(obj2Update.getNome())) {
                List<Cidade> cidades = repository.findByNome(dto.nome(), false);
                for (Cidade cidade : cidades) {
                    if (cidade.getEstado() == obj2Update.getEstado()) {
                        throw new ValidationException("nome", "Nome já cadastrado para o estado informado.");
                    }
                }
            }
        } else {
            List<Cidade> cidades = repository.findByNome(dto.nome(), false);
            for (Cidade cidade : cidades) {
                if (cidade.getEstado().getId() == dto.estado()) {
                    throw new ValidationException("nome", "Nome já cadastrado para o estado informado.");
                }
            }
        }
    }

    @Override
    @Transactional
    public CidadeResponseDTO insert(@Valid CidadeDTO dto) {
        
        valid(dto, null);

        Estado estado = repositoryEstado.findById(dto.estado());

        Cidade novoCidade = new Cidade();
        novoCidade.setNome(dto.nome());
        novoCidade.setEstado(estado);
        novoCidade.setFrete(dto.frete());

        repository.persist(novoCidade);

        return CidadeResponseDTO.valueOf(novoCidade);
    }

    @Override
    @Transactional
    public CidadeResponseDTO update(@Valid CidadeDTO dto, Long id) {
        Cidade cidade = repository.findById(id);
        if (cidade == null) {
            throw new NotFoundException();
        }

        valid(dto, cidade);
        
        Estado estado = repositoryEstado.findById(dto.estado());

        cidade.setNome(dto.nome());
        cidade.setEstado(estado);
        cidade.setFrete(dto.frete());

        return CidadeResponseDTO.valueOf(cidade);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) 
            throw new NotFoundException();
    }

    @Override
    public CidadeResponseDTO findById(Long id) {
        return CidadeResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<CidadeResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome, true).stream()
            .map(e -> CidadeResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<CidadeResponseDTO> findByEstado(Long idEstado) {
        return repository.findByEstado(idEstado).stream()
            .map(e -> CidadeResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<CidadeResponseDTO> findByAll() {
        return repository.listAll().stream()
            .map(e -> CidadeResponseDTO.valueOf(e)).toList();
    }
}
