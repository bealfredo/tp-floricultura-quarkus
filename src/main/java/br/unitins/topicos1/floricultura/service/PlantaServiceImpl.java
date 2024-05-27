package br.unitins.topicos1.floricultura.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.floricultura.dto.PlantaCriarRascunhoDTO;
import br.unitins.topicos1.floricultura.dto.PlantaDTO;
import br.unitins.topicos1.floricultura.dto.PlantaResponseDTO;
import br.unitins.topicos1.floricultura.dto.PlantaUpdateAddRemoveQuantidadeDTO;
import br.unitins.topicos1.floricultura.dto.PlantaUpdateStatusPlantaDTO;
import br.unitins.topicos1.floricultura.form.PlantaImageForm;
import br.unitins.topicos1.floricultura.model.CategoriaPlanta;
import br.unitins.topicos1.floricultura.model.Fornecedor;
import br.unitins.topicos1.floricultura.model.NivelDificuldade;
import br.unitins.topicos1.floricultura.model.NivelToxidade;
import br.unitins.topicos1.floricultura.model.Planta;
import br.unitins.topicos1.floricultura.model.PortePlanta;
import br.unitins.topicos1.floricultura.model.StatusPlanta;
import br.unitins.topicos1.floricultura.model.Tag;
import br.unitins.topicos1.floricultura.model.TipoCategoria;
import br.unitins.topicos1.floricultura.repository.CategoriaPlantaRepository;
import br.unitins.topicos1.floricultura.repository.FornecedorRepository;
import br.unitins.topicos1.floricultura.repository.PlantaRepository;
import br.unitins.topicos1.floricultura.repository.TagRepository;
import br.unitins.topicos1.floricultura.validation.GeneralValidationException;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PlantaServiceImpl implements PlantaService {

    @Inject
    HashService hashService;

    @Inject
    PlantaRepository repository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    CategoriaPlantaRepository categoriaPlantaRepository;

    @Inject
    TagRepository tagRepository;

    @Inject
    PlantaFileService plantaFileService;

    @Inject
    JsonWebToken jwt;

    // @Inject
    // UsuarioService usuarioService;

    @Override
    @Transactional
    public PlantaResponseDTO criarRascunho(@Valid PlantaCriarRascunhoDTO dto) {
        Planta novaPlanta = new Planta();

        CategoriaPlanta categoriaBiologica = categoriaPlantaRepository.findById(dto.idCategoriaBiologica());
        if (categoriaBiologica == null) {
            throw new ValidationException("categoriaBiologica", "A categoria de planta informada não existe.");
        } else if (categoriaBiologica.getTipoCategoria() != TipoCategoria.BIOLOGICA) {
            throw new ValidationException("categoriaBiologica", "A categoria de planta informada não é do biológica.");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(dto.idFornecedor());
        if (fornecedor == null) {
            throw new ValidationException("fornecedor", "O fornecedor informado não existe.");
        }

        novaPlanta.setNomeComum(dto.nomeComum());
        novaPlanta.setNomeCientifico("");
        novaPlanta.setDescricao("");
        novaPlanta.setCodigo("");
        novaPlanta.setImagemPrincipal(null);
        novaPlanta.setImagens(new String[0]);
        
        novaPlanta.setPrecoVenda(0.0);
        novaPlanta.setPrecoCusto(0.0);
        novaPlanta.setDesconto(0.0);
        novaPlanta.setQuantidadeDisponivel(0);
        novaPlanta.setQuantidadeVendido(0);
        novaPlanta.setDataDisponivel(null);
        
        novaPlanta.setFornecedor(fornecedor);
        novaPlanta.setTags(new ArrayList<Tag>());
        novaPlanta.setCategoriaBiologica(categoriaBiologica);

        novaPlanta.setStatusPlanta(StatusPlanta.RASCUNHO);
        novaPlanta.setNivelDificuldade(null);
        novaPlanta.setNivelToxidade(null);
        novaPlanta.setPortePlanta(null);

        novaPlanta.setTempoCrescimento("");
        novaPlanta.setOrigem(null);

        repository.persist(novaPlanta);
        return PlantaResponseDTO.valueOf(novaPlanta);
    }

    @Override
    @Transactional
    public PlantaResponseDTO update(@Valid PlantaDTO dto, Long id) {

        Planta planta = repository.findByIdComBloqueioCompartilhado(id);
        if (planta == null) {
            throw new NotFoundException();
        }

        if (repository.findByCodigo(dto.codigo()) != null && !planta.getCodigo().equals(dto.codigo() )) {
            throw new ValidationException("codigo", "Já existe planta com o código informado.");
        }

        StatusPlanta statusPlanta = StatusPlanta.valueOf(dto.idStatusPlanta());
        if (statusPlanta == null) {
            throw new ValidationException("statusPlanta", "Id para status planta inválido.");
        }

        NivelDificuldade nivelDificuldade = NivelDificuldade.valueOf(dto.nivelDificuldade());
        if (nivelDificuldade == null) {
            throw new ValidationException("nivelDificuldade", "Nível de dificuldade inválido.");
        }

        NivelToxidade nivelToxidade = NivelToxidade.valueOf(dto.nivelToxidade());
        if (nivelToxidade == null) {
            throw new ValidationException("nivelToxidade", "Nível de toxidade inválido.");
        }

        PortePlanta portePlanta = PortePlanta.valueOf(dto.portePlanta());
        if (portePlanta == null) {
            throw new ValidationException("portePlanta", "Porte da planta inválido.");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(dto.idFornecedor());
        if (fornecedor == null) {
            throw new ValidationException("fornecedor", "O fornecedor informado não existe.");
        }

        Set<Tag> tagsSet = new HashSet<>();
        Set<Tag> tagsSetDeOutrasCategorias = new HashSet<>();
        for (Long tagId : dto.idsTags()) {
            Tag tag = tagRepository.findById(tagId);
            if (tag == null) {
                throw new ValidationException("tag", "Uma das tags informadas não existe.");
            }

            if (tag.getCategoriaPlanta() != null && tag.getCategoriaPlanta().getTipoCategoria() == TipoCategoria.BIOLOGICA) {
                if (tag.getCategoriaPlanta().getId() != dto.idCategoriaBiologica()) {
                    tagsSetDeOutrasCategorias.add(tag);
                }
                // throw new ValidationException("tag", "As seguintes tags não pertencem à categoria biológica informada: " + tag.getNome() + ".");
                // tagsSetDeOutrasCategorias.add(tag);
            }
            tagsSet.add(tag);
        }

        if (!tagsSetDeOutrasCategorias.isEmpty()) {
            String tagsSetDeOutrasCategoriasString = String.join(", ", tagsSetDeOutrasCategorias.stream()
                .map(tag -> tag.getNome())
                .collect(Collectors.toList()));
                        
            throw new ValidationException("tag", "As seguintes tags pertecem à categorias biologica diferentes da categoria biológica informada: " + tagsSetDeOutrasCategoriasString + ".");
        }


        List<Tag> tags = new ArrayList<>(tagsSet);

        CategoriaPlanta categoriaBiologica = categoriaPlantaRepository.findById(dto.idCategoriaBiologica());
        if (categoriaBiologica == null) {
            throw new ValidationException("categoriaBiologica", "A categoria de planta informada não existe.");
        } else if (categoriaBiologica.getTipoCategoria() != TipoCategoria.BIOLOGICA) {
            throw new ValidationException("categoriaBiologica", "A categoria de planta informada não é do tipo biológica.");
        }

        // Planta planta = new Planta();
        planta.setNomeComum(dto.nomeComum());
        planta.setNomeCientifico(dto.nomeCientifico());
        planta.setDescricao(dto.descricao());
        planta.setCodigo(dto.codigo());
        // novaPlanta.setImagemPrincipal(dto.imagemPrincipal());
        // novaPlanta.setImagens(dto.imagens());
        planta.setPrecoVenda(dto.precoVenda());
        planta.setPrecoCusto(dto.precoCusto());
        planta.setDesconto(dto.desconto());
        planta.setQuantidadeDisponivel(dto.quantidadeDisponivel());
        // planta.setQuantidadeVendido(dto.quantidadeVendido());
        planta.setOrigem(dto.origem());
        planta.setTempoCrescimento(dto.tempoCrescimento());
        planta.setStatusPlanta(statusPlanta);
        planta.setNivelDificuldade(nivelDificuldade);
        planta.setNivelToxidade(nivelToxidade);
        planta.setPortePlanta(portePlanta);
        planta.setDataDisponivel(dto.dataDisponivel());
        planta.setFornecedor(fornecedor);
        planta.setTags(tags);
        planta.setCategoriaBiologica(categoriaBiologica);

        repository.persist(planta);
        return PlantaResponseDTO.valueOf(planta);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Planta planta = repository.findByIdComBloqueioCompartilhado(id);
        if (planta == null) {
            throw new NotFoundException();
        }

        repository.delete(planta);
    }

    @Override
    public List<PlantaResponseDTO> findAll() {
        return repository.listAll().stream()
            .map(planta -> PlantaResponseDTO.valueOf(planta)).toList();
    }

    @Override
    public PlantaResponseDTO findById(Long id) {
        Planta planta = repository.findById(id);
        if (planta == null) {
            throw new NotFoundException();
        }

        return PlantaResponseDTO.valueOf(planta);
    }

    @Override
    public List<PlantaResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome)
            .stream()
            .map(planta -> PlantaResponseDTO.valueOf(planta))
            .toList();
    }

    // @Override
    // public ProdutoResponseDTO findByCodigo(String codigo) {
    //     Produto produto = repository.findByCodigo(codigo);
    //     if (produto == null) {
    //         throw new NotFoundException();
    //     }

    //     return ProdutoResponseDTO.valueOf(produto);
    // }

    // @Override
    // public List<ProdutoResponseDTO> findByTipoProduto(Long id) {
    //     TipoProduto tp = tagRepository.findById(id);
    //     if (tp == null) {
    //         throw new NotFoundException();
    //     }

    //     return repository.findByTipoProduto(id)
    //         .stream()
    //         .map(produto -> ProdutoResponseDTO.valueOf(produto))
    //         .toList();
    // }

    // @Override
    // public List<ProdutoResponseDTO> findByFornecedor(Long id) {
    //     Fornecedor fornecedor = fornecedorRepository.findById(id);
    //     if (fornecedor == null) {
    //         throw new NotFoundException();
    //     }

    //     return repository.findByFornecedor(id)
    //         .stream()
    //         .map(produto -> ProdutoResponseDTO.valueOf(produto))
    //         .toList();
    // }

    // @Override
    // public List<ProdutoResponseDTO> findByStatusProduto(Integer id) {
    //     StatusProduto status = StatusProduto.valueOf(id);
    //     if (status == null) {
    //         throw new NotFoundException();
    //     }

    //     return repository.findByStatusProduto(status)
    //         .stream()
    //         .map(produto -> ProdutoResponseDTO.valueOf(produto))
    //         .toList();
    // }

    @Override
    @Transactional
    public PlantaResponseDTO adicionarImagem(PlantaImageForm form, Long id) {
        Planta planta = repository.findById(id);
        if (planta == null) {
            throw new NotFoundException();
        }

        String nomeImagem = "";

        try {
            nomeImagem = plantaFileService.salvar(planta.getId(), form.getImagem());
        } catch (Exception e) {
            throw new GeneralValidationException("Imagem planta", "Erro ao adicionar a imagem: " + e.getMessage());
        }

        List<String> imagens = new ArrayList<>(Arrays.asList(planta.getImagens()));
        imagens.add(nomeImagem);

        planta.setImagens(imagens.toArray(new String[0]));

        if (planta.getImagemPrincipal() == null && imagens.size() == 1) {
            planta.setImagemPrincipal(nomeImagem);
        }

        return PlantaResponseDTO.valueOf(planta);
    }

    @Override
    public File downloadImagem(String nomeImagem, Long id) {
        Planta planta = repository.findById(id);
        if (planta == null) {
            throw new NotFoundException();
        }

        // String login = jwt.getSubject();
        // UsuarioResponseDTO usuarioResponseDTO = usuarioService.fin(id);

        // if (usuarioResponseDTO.tipoUsuario() == TipoUsuario.CLIENTE) {
            // if (!(planta.getStatusProduto() == StatusProduto.ATIVO)) {
            //     throw new GeneralValidationException("Imagem produto", "O produto não está ativo");
            // }
        // }

        if (planta.getImagens() == null || planta.getImagens().length == 0) {
            throw new GeneralValidationException("Imagem produto", "O produto não possui imagem");
        }

        for (String imagem : planta.getImagens()) {
            if (imagem.equals(nomeImagem)) {
                return plantaFileService.obter(planta.getId(), nomeImagem);
            }
        }

        throw new GeneralValidationException("Imagem produto", "A imagem não foi encontrada");
    }

    @Override
    @Transactional
    public void deleteImagem(String nomeImagem, Long id) {
        Planta planta = repository.findById(id);
        if (planta == null) {
            throw new NotFoundException();
        }

        List<String> imagens = new ArrayList<>(Arrays.asList(planta.getImagens()));
        Boolean imagemEncontrada = false;
        for (String imagem : imagens) {
            if (imagem.equals(nomeImagem)) {
                imagemEncontrada = true;
                break;
            }
        }
        if (!imagemEncontrada) {
            throw new GeneralValidationException("Imagem planta", "A imagem não foi encontrada");
        }

        plantaFileService.apagar(planta.getId(), nomeImagem);
        imagens.remove(nomeImagem);
        planta.setImagens(imagens.toArray(new String[0]));
        
        if (planta.getImagemPrincipal().equals(nomeImagem)) {
            planta.setImagemPrincipal(null);
        }

    }

    @Override
    @Transactional
    public void definirImagemPrincipal(String nomeImagem, Long id) {
        Planta planta = repository.findById(id);
        if (planta == null) {
            throw new NotFoundException();
        }

        List<String> imagens = new ArrayList<>(Arrays.asList(planta.getImagens()));
        Boolean imagemEncontrada = false;
        for (String imagem : imagens) {
            if (imagem.equals(nomeImagem)) {
                imagemEncontrada = true;
                break;
            }
        }
        if (!imagemEncontrada) {
            throw new GeneralValidationException("Imagem planta", "A imagem não foi encontrada");
        }

        planta.setImagemPrincipal(nomeImagem);
    }


    @Override
    @Transactional
    public void updateStatusPlanta(PlantaUpdateStatusPlantaDTO dto, Long id) {
        Planta planta = repository.findByIdComBloqueioCompartilhado(id);
        if (planta == null) {
            throw new NotFoundException();
        }

        StatusPlanta statusPlanta = StatusPlanta.valueOf(dto.idStatus());
        if(statusPlanta == null) {
            throw new ValidationException("statusPlanta", "O status informado é inválido.");
        }

        planta.setStatusPlanta(statusPlanta);
    }

    @Override
    @Transactional
    public void updateAddRemoveQuantidade(PlantaUpdateAddRemoveQuantidadeDTO dto, Long id) {
        Planta planta = repository.findByIdComBloqueioCompartilhado(id);
        if (planta == null) {
            throw new NotFoundException();
        }

        Integer newQuantidade = planta.getQuantidadeDisponivel() + dto.quantidade();

        if (newQuantidade < 0) {
            throw new ValidationException("quantidade", "A nova quantidade disponível não pode ser negativa.");
        }

        planta.setQuantidadeDisponivel(dto.quantidade() + planta.getQuantidadeDisponivel());
    }


    
}
