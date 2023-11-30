package br.unitins.topicos1.floricultura.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.topicos1.floricultura.dto.ItemVendaDTO;
import br.unitins.topicos1.floricultura.dto.VendaDTO;
import br.unitins.topicos1.floricultura.dto.VendaResponseDTO;
import br.unitins.topicos1.floricultura.dto.VendaUpdateStatusDTO;
import br.unitins.topicos1.floricultura.model.Cidade;
import br.unitins.topicos1.floricultura.model.Endereco;
import br.unitins.topicos1.floricultura.model.HistoricoStatus;
import br.unitins.topicos1.floricultura.model.ItemVenda;
import br.unitins.topicos1.floricultura.model.Produto;
import br.unitins.topicos1.floricultura.model.StatusProduto;
import br.unitins.topicos1.floricultura.model.StatusVenda;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.model.Venda;
import br.unitins.topicos1.floricultura.repository.CidadeRepository;
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

    // ! to delete
    @Inject
    CidadeRepository cidadeRepository;

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

    private String gerarCodigoVenda() {
        LocalDateTime dataAtual = LocalDateTime.now();
        DateTimeFormatter formatoAnoMesDia = DateTimeFormatter.ofPattern("yyyyMMdd");
        String anoMesDia = dataAtual.format(formatoAnoMesDia);

        String prefixo = "VE";
        
        String qtVendasOfDay =  String.format("%04d", vendaRepository.qtVendasOfDay());

        return prefixo + anoMesDia + qtVendasOfDay;
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

        // endereço de entrega
        LOG.infof("Verificando se o endereço para entrega é válido. idEndereco: %d", dto.idEnderecoEntrega());
        // !Verificar endereço e copiar o endereço escolhido para um novo endereço
        
        LOG.info("Gerando uma cópia do endereço selecionado.");
        Endereco enderecoEntrega = new Endereco();
        enderecoEntrega.setCodigo("111111");
        enderecoEntrega.setRua("Rua Castelo Branco");
        enderecoEntrega.setBairro("Plano Diretor Sul");
        enderecoEntrega.setNumeroLote("19");
        enderecoEntrega.setComplemento(null);

        Cidade cidadeEndereçoEntrega = cidadeRepository.findById(Long.valueOf(1));
        enderecoEntrega.setCidade(cidadeEndereçoEntrega);

        Venda venda = new Venda();
        venda.setUsuario(usuario);
        venda.setEndereco(enderecoEntrega);
        venda.setDataHora(LocalDateTime.now());

        LOG.info("Criando a lista de itens da venda.");
        List<ItemVenda> itensVenda = new ArrayList<>();
        for (ItemVendaDTO item : dto.itensVenda()) {
            Produto produto = produtoRepository.findByIdComBloqueioCompartilhado(item.produto());
            if (produto == null) {
                LOG.errorf("Produto com o id não encontrado. ID do produto: %s", item.produto().toString());
                throw new ValidationException("produto", "Um dos produtos informado não foi encontrado");
            }

            LOG.infof("Verificando se o produto está ativo. Produto: %s, ID: %d", produto.getNome(), item.produto());
            if (produto.getStatusProduto() != StatusProduto.ATIVO) {
                LOG.errorf("Produto não está ativo. Produto: %s, ID: %d", produto.getNome(), item.produto());
                throw new ValidationException("produto", "Um dos produtos informados não está ativo");
            }

            LOG.infof("Verificando estoque do produto. Produto: %s, ID: %d", produto.getNome(), item.produto());
            if(produto.getQuantidadeDisponivel() < item.quantidade()) {
                LOG.errorf("Produto com quantidade insuficiente para venda. Produto: %s, ID: %d", produto.getNome(), item.produto());
                throw new ValidationException("produto", "Um dos produtos informados não possui quantidade suficiente para a venda. ID do produto: " + produto.getId());
            }

            LOG.infof("Atualizando estoque do produto. Produto: %s, ID: %d", produto.getNome(), item.produto());
            produto.setQuantidadeDisponivel(produto.getQuantidadeDisponivel() - item.quantidade());

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setQuantidade(item.quantidade());
            itemVenda.setVenda(venda);
            itemVenda.setProduto(produto);

            Double precoComDesconto = produto.getPrecoVenda() - (produto.getPrecoVenda() * produto.getDesconto());
            itemVenda.setPreco(precoComDesconto);
            itensVenda.add(itemVenda);

            LOG.infof("Item adicionado à lista de venda - Produto: %s, ID: %d, Quantidade: %d, Preço: %f",
                    produto.getNome(), produto.getId(), itemVenda.getQuantidade(), itemVenda.getPreco());
        }
        venda.setItensVenda(itensVenda);

        Double total = calcularTotal(itensVenda);
        LOG.infof("Total da venda calculado. Total: %f", total);
        venda.setTotalVenda(total);
 
        venda.setCodigo(gerarCodigoVenda());
        LOG.infof("Código de venda gerado. Código: %s", venda.getCodigo());

        String chavePix = "00020126580014BR.GOV.BCB.PIX0136089b6a84-1cbc-474e-956c-c36768a48c025204000053039865802BR5925Alfredo de Souza Aguiar N6009SAO PAULO621405100JVdOWgy4g6304B41D";
        venda.setChavePix(chavePix);
        LOG.infof("Chave pix gerada. Chave: %s", venda.getChavePix());

        LOG.info("Iniciando o histórico da venda com o primeiro status: " + StatusVenda.AGUARDANDO_PAGAMENTO.getLabel());
        ArrayList<HistoricoStatus> historicoStatus = new ArrayList<>();
        HistoricoStatus historico1 = new HistoricoStatus();
        historico1.setData(LocalDateTime.now());
        historico1.setStatusVenda(StatusVenda.AGUARDANDO_PAGAMENTO);
        historico1.setVenda(venda);
        historicoStatus.add(historico1);
        
        venda.setHistoricoStatus(historicoStatus);

        LOG.info("Salvando no banco de dados.");
        vendaRepository.persist(venda);

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

    @Override
    public VendaResponseDTO updateStatusVenda(VendaUpdateStatusDTO dto, Long id) {
        LOG.info("Buscando venda pelo ID. ID: " + id);
        Venda venda = vendaRepository.findById(id);
        if (venda == null) {
            LOG.error("Venda não encontrada pelo ID. ID: " + id);
            throw new NotFoundException();
        }

        LOG.info("Adicionando o status ao histórico.");
        HistoricoStatus novoStatus = new HistoricoStatus();
        novoStatus.setData(LocalDateTime.now());
        novoStatus.setStatusVenda(StatusVenda.valueOf(dto.novoStatusId()));
        novoStatus.setVenda(venda);
        venda.getHistoricoStatus().add(novoStatus);

        vendaRepository.persist(venda);
        LOG.info("Histórico adicionado com sucesso." + venda.getHistoricoStatus().size());

        return VendaResponseDTO.valueOf(venda);
    }

    // public List<VendaResponseDTO> findByStatus() {

    // }

}
