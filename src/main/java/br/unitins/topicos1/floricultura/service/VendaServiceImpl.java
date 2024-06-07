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
import br.unitins.topicos1.floricultura.model.Cliente;
import br.unitins.topicos1.floricultura.model.Endereco;
import br.unitins.topicos1.floricultura.model.HistoricoStatus;
import br.unitins.topicos1.floricultura.model.ItemVenda;
import br.unitins.topicos1.floricultura.model.Planta;
import br.unitins.topicos1.floricultura.model.StatusPlanta;
import br.unitins.topicos1.floricultura.model.StatusVenda;
import br.unitins.topicos1.floricultura.model.Usuario;
import br.unitins.topicos1.floricultura.model.Venda;
import br.unitins.topicos1.floricultura.repository.CidadeRepository;
import br.unitins.topicos1.floricultura.repository.ClienteRepository;
import br.unitins.topicos1.floricultura.repository.PlantaRepository;
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
    PlantaRepository plantaRepository;

    // ! to delete
    @Inject
    CidadeRepository cidadeRepository;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ClienteRepository clienteRepository;

    private static Double calcularTotal(List<ItemVenda> itens, Double frete) {
        Double total = 0.0;
        for (ItemVenda item : itens) {
            total += item.getPreco() * item.getQuantidade();
        }
        return total + frete;
    }

    private String gerarCodigoVenda() {
        LocalDateTime dataAtual = LocalDateTime.now();
        DateTimeFormatter formatoAnoMesDia = DateTimeFormatter.ofPattern("yyyyMMdd");
        String anoMesDia = dataAtual.format(formatoAnoMesDia);

        String prefixo = "VE";
        
        String qtVendasOfDay =  String.format("%04d", vendaRepository.qtVendasOfDay() + 1);

        return prefixo + anoMesDia + qtVendasOfDay;
    }

    @Override
    @Transactional // commit e rollback
    public VendaResponseDTO insert(VendaDTO dto, String login) {
        LOG.infof("Iniciando a venda. Login: %s", login);

        // buscando cliente
        Cliente cliente = clienteRepository.findByLogin(login);
        if (cliente == null) {
            LOG.errorf("Cliente não encontrado. Login: %s", login);
            throw new ValidationException("token", "O cliente não foi encontrado.");
        }

        LOG.infof("Cliente encontrado. ID: %d", cliente.getId());

        // endereço de entrega
        LOG.infof("Verificando se o endereço para entrega é válido. idEndereco: %d", dto.idEnderecoEntrega());
        // !Verificar endereço e copiar o endereço escolhido para um novo endereço

        // endereco do cliente
        Endereco enderecoCliente = cliente.getListaEndereco().stream()
            .filter(endereco -> endereco.getId().equals(dto.idEnderecoEntrega()))
            .findFirst()
            .orElse(null);
        
        if (enderecoCliente == null) {
            LOG.errorf("Endereço não encontrado. ID do endereço: %d", dto.idEnderecoEntrega());
            throw new ValidationException("idEnderecoEntrega", "Endereço não encontrado.");
        }
        
        LOG.info("Gerando uma cópia do endereço selecionado.");
        Endereco enderecoEntrega = new Endereco();
        enderecoEntrega.setNome(enderecoCliente.getNome());
        enderecoEntrega.setRua(enderecoCliente.getRua());
        enderecoEntrega.setBairro(enderecoCliente.getBairro());
        enderecoEntrega.setNumeroLote(enderecoCliente.getNumeroLote());
        enderecoEntrega.setComplemento(enderecoCliente.getComplemento());
        enderecoEntrega.setCep(enderecoCliente.getCep());

        Cidade cidadeEndereçoEntrega = cidadeRepository.findById(Long.valueOf(1));
        enderecoEntrega.setCidade(cidadeEndereçoEntrega);

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setEndereco(enderecoEntrega);
        venda.setDataHora(LocalDateTime.now());

        LOG.info("Criando a lista de itens da venda.");
        List<ItemVenda> itensVenda = new ArrayList<>();
        for (ItemVendaDTO item : dto.itensVenda()) {
            Planta planta = plantaRepository.findByIdComBloqueioCompartilhado(item.planta());
            if (planta == null) {
                LOG.errorf("Planta com o id não encontrado. ID do planta: %s", item.planta().toString());
                throw new ValidationException("planta", "Uma das plantas informadas não foi encontrado");
            }

            LOG.infof("Verificando se o planta está ativo. Planta: %s, ID: %d", planta.getNomeComum(), item.planta());
            if (planta.getStatusPlanta() != StatusPlanta.ATIVO) {
                LOG.errorf("Planta não está ativo. Planta: %s, ID: %d", planta.getNomeComum(), item.planta());
                throw new ValidationException("planta", "Uma das plantas informadas não está ativo");
            }

            LOG.infof("Verificando estoque do planta. Planta: %s, ID: %d", planta.getNomeComum(), item.planta());
            if(planta.getQuantidadeDisponivel() < item.quantidade()) {
                LOG.errorf("Planta com quantidade insuficiente para venda. Planta: %s, ID: %d", planta.getNomeComum(), item.planta());
                throw new ValidationException("planta", "Uma das plantas informadas não possui quantidade suficiente para a venda. ID do planta: " + planta.getId());
            }

            LOG.infof("Atualizando estoque do planta. Planta: %s, ID: %d", planta.getNomeComum(), item.planta());
            planta.setQuantidadeDisponivel(planta.getQuantidadeDisponivel() - item.quantidade());

            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setQuantidade(item.quantidade());
            itemVenda.setVenda(venda);
            itemVenda.setPlanta(planta);

            Double totalDesconto = planta.getPrecoVenda() * planta.getDesconto();
            Double precoComDesconto = planta.getPrecoVenda() - totalDesconto;
            itemVenda.setPreco(precoComDesconto);
            itensVenda.add(itemVenda);

            // // preco com desconto não bater com o calculado
            // if (precoComDesconto != (itemVenda.getPreco() - (itemVenda.getPreco() * planta.getDesconto())) ) {
            //     LOG.errorf("Preço com desconto não bate com o calculado. Planta: %s, ID: %d, Preço: %f, Preço calculado: %f",
            //             planta.getNomeComum(), planta.getId(), itemVenda.getPreco(), precoComDesconto);
            //     throw new ValidationException("planta", "O preço calculado mudou");
            // }

            LOG.infof("Item adicionado à lista de venda - Planta: %s, ID: %d, Quantidade: %d, Preço: %f",
                    planta.getNomeComum(), planta.getId(), itemVenda.getQuantidade(), itemVenda.getPreco());
        }
        venda.setItensVenda(itensVenda);

        Double total = calcularTotal(itensVenda, enderecoCliente.getCidade().getFrete());
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

        venda.setLastStatus(historico1);

        LOG.info("Salvando no banco de dados.");
        vendaRepository.persist(venda);

        return VendaResponseDTO.valueOf(venda);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        LOG.info("Iniciando deleção da venda. Id da venda: " + id);
        if (!vendaRepository.deleteById(id)) {
            LOG.error("Venda não encontrada. Id da venda: " + id);
            throw new NotFoundException();
        }

        LOG.info("Deletando venda. Id da venda: " + id);
    }

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

        LOG.info("Atualizando ultimo status da venda.");
        venda.setLastStatus(novoStatus);

        return VendaResponseDTO.valueOf(venda);
    }

    @Override
    public List<VendaResponseDTO> findByLastStatus(Integer id) {
        StatusVenda status = StatusVenda.valueOf(id);
        if (status == null) {
            throw new NotFoundException();
        }

        return vendaRepository.findByLastStatus(status)
            .stream()
            .map(venda -> VendaResponseDTO.valueOf(venda))
            .toList();
    }

    public List<VendaResponseDTO> minhasCompras() {
        String login = jwt.getSubject();
        Cliente cliente = clienteRepository.findByLogin(login);
        if (cliente == null) {
            throw new NotFoundException();
        }

        return vendaRepository.findByCliente(cliente.getId())
            .stream()
            .map(venda -> VendaResponseDTO.valueOf(venda))
            .toList();
    }


}
