package br.unitins.topicos1.floricultura.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.topicos1.floricultura.dto.ItemVendaDTO;
import br.unitins.topicos1.floricultura.dto.VendaDTO;
import br.unitins.topicos1.floricultura.dto.VendaResponseDTO;
import br.unitins.topicos1.floricultura.model.ItemVenda;
import br.unitins.topicos1.floricultura.model.Produto;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.model.Venda;
import br.unitins.topicos1.floricultura.repository.ProdutoRepository;
import br.unitins.topicos1.floricultura.repository.UsuarioRepository;
import br.unitins.topicos1.floricultura.repository.VendaRepository;
import br.unitins.topicos1.floricultura.resource.AuthResource;
import br.unitins.topicos1.floricultura.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class VendaServiceImpl implements VendaService {

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Inject
    VendaRepository vendaRepository;

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioRepository usuarioRepository;

    private static Double calcularTotal(List<ItemVenda> itens) {
        Double total = 0.0;
        for (ItemVenda item : itens) {
            total += item.getPreco() * item.getQuantidade();
        }
        return total;
    }

    @Override
    @Transactional // commit e rollback
    public VendaResponseDTO insert(VendaDTO dto, String login) {
        LOG.infof("Iniciando a venda. Login: %s", login);

        // buscando usuario
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null) {
            LOG.errorf("Usuário não encontrado. Login: %s", login);
            throw new ValidationException("token", "O usuario não foi encontrado.");
        }

        LOG.infof("Usuário encontrado. ID: %d", usuario.getId());

        Venda venda = new Venda();
        venda.setUsuario(usuario);
        venda.setDataHora(LocalDateTime.now());

        LOG.info("Criando a lista de itens da venda.");
        List<ItemVenda> itensVenda = new ArrayList<>();
        for (ItemVendaDTO item : dto.itensVenda()) {
            Produto produto = produtoRepository.findById(item.produto());
            if (produto == null) {
                LOG.errorf("Produto com o id não encontrado. ID do produto: %s", item.produto().toString());
                throw new ValidationException("produto", "Um dos produtos informado não foi encontrado");
            }

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setQuantidade(item.quantidade());
            itemVenda.setVenda(venda);
            itemVenda.setProduto(produto);

            Double precoComDesconto = produto.getPrecoVenda() - (produto.getPrecoVenda() * produto.getDesconto());
            itemVenda.setPreco(precoComDesconto);
            itensVenda.add(itemVenda);

            LOG.infof("Item adicionado à lista de venda - Produto: %s, ID: %d, Quantidade: %d, Preço: %f",
                    produto.getNome(), produto.getId(), itemVenda.getQuantidade(), itemVenda.getPreco());
            // ! falta atualizar o estoque
        }
        venda.setItensVenda(itensVenda);

        Double total = calcularTotal(itensVenda);
        LOG.infof("Total da venda calculado. Total: %f", total);
        venda.setTotalVenda(total);

        LOG.info("Salvando no banco de dados.");
        vendaRepository.persist(venda);

        // ! atualizando o estoque

        // !chave pix venda

        return VendaResponseDTO.valueOf(venda);
    }

    // // @Override
    // // @Transactional
    // // public void delete(Long id) {
    // // }

    @Override
    public List<VendaResponseDTO> findAll() {
        LOG.info("Encontrando todas as vendas");
        return vendaRepository.listAll().stream()
                .map(venda -> VendaResponseDTO.valueOf(venda)).toList();
    }

    @Override
    public VendaResponseDTO findById(Long id) {
        LOG.info("Buscando venda pelo ID. ID: " + id);
        Venda venda = vendaRepository.findById(id);
        if (venda == null) {
            LOG.error("Venda não encontrada pelo ID. ID: " + id);
            throw new NotFoundException();
        }
        LOG.info("Venda encontrada. ID: " + id);
        return VendaResponseDTO.valueOf(venda);
    }

    @Override
    public List<VendaResponseDTO> findByUsuario(Long id) throws Exception {
        LOG.info("Buscando venda pelo usuario. ID do usario: " + id);

        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            LOG.error("Usuario não encontrado pelo ID. ID do usuario: " + id);
            throw new Exception("O usuario informado não foi encontrado");
        }

        return vendaRepository.findByUsuario(usuario)
            .stream()
            .map(venda -> VendaResponseDTO.valueOf(venda))
            .toList();
    }

}
