package br.unitins.topicos1.floricultura.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.floricultura.dto.ProdutoDTO;
import br.unitins.topicos1.floricultura.dto.ProdutoResponseDTO;
import br.unitins.topicos1.floricultura.form.ProdutoImageForm;
import br.unitins.topicos1.floricultura.model.Fornecedor;
import br.unitins.topicos1.floricultura.model.Produto;
import br.unitins.topicos1.floricultura.model.StatusProduto;
import br.unitins.topicos1.floricultura.model.TipoProduto;
import br.unitins.topicos1.floricultura.repository.FornecedorRepository;
import br.unitins.topicos1.floricultura.repository.ProdutoRepository;
import br.unitins.topicos1.floricultura.repository.TipoProdutoRepository;
import br.unitins.topicos1.floricultura.validation.GeneralValidationException;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    HashService hashService;

    @Inject
    ProdutoRepository repository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    TipoProdutoRepository tipoProdutoRepository;

    @Inject
    ProdutoFileService produtoFileService;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    @Override
    @Transactional
    public ProdutoResponseDTO insert(@Valid ProdutoDTO dto) {

        if (repository.findByCodigo(dto.codigo()) != null) {
            throw new ValidationException("codigo", "Já existe produto com o código informado.");
        }

        StatusProduto statusProduto = StatusProduto.valueOf(dto.idStatusProduto());
        if (statusProduto == null) {
            throw new ValidationException("statusProduto", "Id para status produto inválido.");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(dto.idFornecedor());
        if (fornecedor == null) {
            throw new ValidationException("fornecedor", "O fornecedor informado não existe.");
        }

        List<TipoProduto> tipoProduto = new ArrayList<TipoProduto>();
        for (Long tipoProdutoId : dto.idsTipoProduto()) {
            TipoProduto oneTipoProduto = tipoProdutoRepository.findById(tipoProdutoId);
            if(oneTipoProduto == null) {
                throw new ValidationException("tipoProduto", "Um dos tipos de produto informado não existe.");
            }
            tipoProduto.add(oneTipoProduto);
        }

        Produto novoProduto = new Produto();
        novoProduto.setNome(dto.nome());
        novoProduto.setDescricao(dto.descricao());
        novoProduto.setCodigo(dto.codigo());
        novoProduto.setStatusProduto(statusProduto);
        novoProduto.setImagem(dto.imagem());
        novoProduto.setPrecoVenda(dto.precoVenda());
        novoProduto.setPrecoCusto(dto.precoCusto());
        novoProduto.setDesconto(dto.desconto());
        novoProduto.setQuantidadeDisponivel(dto.quantidadeDisponivel());
        novoProduto.setDataDisponivel(dto.dataDisponivel());
        novoProduto.setFornecedor(fornecedor);
        novoProduto.setTipoProduto(tipoProduto);

        repository.persist(novoProduto);
        return ProdutoResponseDTO.valueOf(novoProduto);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO update(ProdutoDTO dto, Long id) {

        Produto produto = repository.findById(id);
        if (produto == null) {
            throw new NotFoundException();
        }

        StatusProduto statusProduto = StatusProduto.valueOf(dto.idStatusProduto());
        if (statusProduto == null) {
            throw new ValidationException("statusProduto", "Id para status produto inválido.");
        }

        if (repository.findByCodigo(dto.codigo()) != null) {
            throw new ValidationException("codigo", "Já existe produto com o código informado.");
        }

        Fornecedor fornecedor = fornecedorRepository.findById(dto.idFornecedor());
        if (fornecedor == null) {
            throw new ValidationException("fornecedor", "O fornecedor informado não existe.");
        }

        List<TipoProduto> tipoProduto = new ArrayList<TipoProduto>();
        for (Long tipoProdutoId : dto.idsTipoProduto()) {
            TipoProduto oneTipoProduto = tipoProdutoRepository.findById(tipoProdutoId);
            if(oneTipoProduto == null) {
                throw new ValidationException("tipoProduto", "Um dos tipos de produto informado não existe.");
            }
            tipoProduto.add(oneTipoProduto);
        }

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setCodigo(dto.codigo());
        produto.setStatusProduto(statusProduto);
        produto.setImagem(dto.imagem());
        produto.setPrecoVenda(dto.precoVenda());
        produto.setPrecoCusto(dto.precoCusto());
        produto.setDesconto(dto.desconto());
        produto.setQuantidadeDisponivel(dto.quantidadeDisponivel());
        produto.setDataDisponivel(dto.dataDisponivel());
        produto.setFornecedor(fornecedor);
        produto.setTipoProduto(tipoProduto);
        
        return ProdutoResponseDTO.valueOf(produto);
    }

    // @Override
    // @Transactional
    // public void delete(Long id) {
    // }

    @Override
    public List<ProdutoResponseDTO> findAll() {
        return repository.listAll().stream()
            .map(produto -> ProdutoResponseDTO.valueOf(produto)).toList();
    }

    @Override
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = repository.findById(id);
        if (produto == null) {
            throw new NotFoundException();
        }

        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    public List<ProdutoResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome)
            .stream()
            .map(produto -> ProdutoResponseDTO.valueOf(produto))
            .toList();
    }

    @Override
    public ProdutoResponseDTO findByCodigo(String codigo) {
        Produto produto = repository.findByCodigo(codigo);
        if (produto == null) {
            throw new NotFoundException();
        }

        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    public List<ProdutoResponseDTO> findByTipoProduto(Long id) {
        TipoProduto tp = tipoProdutoRepository.findById(id);
        if (tp == null) {
            throw new NotFoundException();
        }

        return repository.findByTipoProduto(id)
            .stream()
            .map(produto -> ProdutoResponseDTO.valueOf(produto))
            .toList();
    }

    @Override
    public List<ProdutoResponseDTO> findByFornecedor(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id);
        if (fornecedor == null) {
            throw new NotFoundException();
        }

        return repository.findByFornecedor(id)
            .stream()
            .map(produto -> ProdutoResponseDTO.valueOf(produto))
            .toList();
    }

    @Override
    public List<ProdutoResponseDTO> findByStatusProduto(Integer id) {
        StatusProduto status = StatusProduto.valueOf(id);
        if (status == null) {
            throw new NotFoundException();
        }

        return repository.findByStatusProduto(status)
            .stream()
            .map(produto -> ProdutoResponseDTO.valueOf(produto))
            .toList();
    }

    @Override
    @Transactional
    public ProdutoResponseDTO salvarImagem(ProdutoImageForm form, Long id) {
        Produto produto = repository.findById(id);
        if (produto == null) {
            throw new NotFoundException();
        }

        String nomeImagem = "";

        try {
            nomeImagem = produtoFileService.salvar(form.getNomeImagem(), form.getImagem());
        } catch (Exception e) {
            throw new GeneralValidationException("Imagem produto", "Erro ao salvar a imagem");
        }

        produto.setImagem(nomeImagem);

        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    public File downloadImagem(Long id) {
        Produto produto = repository.findById(id);
        if (produto == null) {
            throw new NotFoundException();
        }

        // String login = jwt.getSubject();
        // UsuarioResponseDTO usuarioResponseDTO = usuarioService.fin(id);

        // if (usuarioResponseDTO.tipoUsuario() == TipoUsuario.CLIENTE) {
            if (!(produto.getStatusProduto() == StatusProduto.ATIVO)) {
                throw new GeneralValidationException("Imagem produto", "O produto não está ativo");
            }
        // }

        if (produto.getImagem() == null) {
            throw new GeneralValidationException("Imagem produto", "O produto não possui imagem");
        }

        return produtoFileService.obter(produto.getImagem());
    }

    
}
