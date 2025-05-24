package br.unitins.topicos1;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.Startup;

import java.util.logging.Logger;

@ApplicationScoped
public class DataInitializer {
    
    private static final Logger LOGGER = Logger.getLogger(DataInitializer.class.getName());
    
    @Inject
    EntityManager em;
    
    @Transactional
    public void onStart(@Observes StartupEvent ev) {
        LOGGER.info("=========== Iniciando inserção de dados ===========");
        
        try {
            // Verifica se já existem dados (para evitar duplicação)
            Long telefoneCount = (Long) em.createQuery("SELECT COUNT(t) FROM Telefone t").getSingleResult();
            
            if (telefoneCount > 0) {
                LOGGER.info("Dados já existem no banco de dados. Pulando inserção.");
                return;
            }
            
            // Executando as consultas SQL em ordem
            
            // Telefones
            executeSQL("INSERT INTO telefone(ddd, numero) VALUES (63, 912345678)");
            executeSQL("INSERT INTO telefone(ddd, numero) VALUES (62, 912345622)");
            executeSQL("INSERT INTO telefone(ddd, numero) VALUES (21, 912345621)");
            executeSQL("INSERT INTO telefone(ddd, numero) VALUES (11, 912345611)");
            executeSQL("INSERT INTO telefone(ddd, numero) VALUES (11, 912345656)");
            
            // Fornecedores
            executeSQL("INSERT INTO fornecedor(id_telefone, nome, email, cnpj) VALUES (1, 'Natureza Verde', 'nverde@gmail.com', '11145678000123')");
            executeSQL("INSERT INTO fornecedor(id_telefone, nome, email, cnpj) VALUES (2, 'Plantas PN', 'pnplants@gmail.com', '22234567000123')");
            executeSQL("INSERT INTO fornecedor(id_telefone, nome, email, cnpj) VALUES (null, 'BomVerder', 'bonve@gmail.com', '33345678000123')");
            executeSQL("INSERT INTO fornecedor(id_telefone, nome, email, cnpj) VALUES (null, 'Plnts', 'plants@gmail.com', '44445678000123')");
            
            // Categorias de plantas
            executeSQL("INSERT INTO categoriaplanta (tipocategoria, nome, ativa, prioridade, descricao) VALUES " +
                    "(1, 'Árvores', true, 3, 'Esta categoria engloba uma variedade de árvores provenientes de diferentes espécies, proporcionando opções diversificadas para seu jardim ou espaço verde.')");
            executeSQL("INSERT INTO categoriaplanta (tipocategoria, nome, ativa, prioridade, descricao) VALUES " +
                    "(1, 'Flores', false, 2, 'Esta categoria engloba uma variedade de flores para alegrar e decorar diferentes ambientes.')");
            executeSQL("INSERT INTO categoriaplanta (tipocategoria, nome, ativa, prioridade, descricao) VALUES " +
                    "(1, 'Cactos', true, 4, 'Esta categoria engloba uma variedade de cactos, ideais para ambientes secos e com pouca manutenção.')");
            executeSQL("INSERT INTO categoriaplanta (tipocategoria, nome, ativa, prioridade, descricao) VALUES " +
                    "(2, 'Novidades', false, 100, 'Esta categoria abrange todas as novidades em plantas e acessórios para jardim.')");
            executeSQL("INSERT INTO categoriaplanta (tipocategoria, nome, ativa, prioridade, descricao) VALUES " +
                    "(2, 'Plantas por Regiões Brasileiras', true, 1, 'Esta categoria oferece plantas específicas das regiões do Brasil.')");
            
            // Tags
            executeSQL("INSERT INTO tag (id_categoriaplanta, nome, ativa, prioridade, descricao) VALUES " +
                    "(1, 'Árvores Frutíferas', true, 1, 'Árvores que produzem frutos comestíveis, oferecendo uma opção funcional e decorativa para diversos ambientes.')");
            executeSQL("INSERT INTO tag (id_categoriaplanta, nome, ativa, prioridade, descricao) VALUES " +
                    "(2, 'Flores Decorativas', false, 2, 'Flores decorativas, ideais para trazer charme e colorido a diversos ambientes.')");
            executeSQL("INSERT INTO tag (id_categoriaplanta, nome, ativa, prioridade, descricao) VALUES " +
                    "(2, 'Flores Azuis', true, 1, 'Flores azuis, ideais para trazer charme e colorido a diversos ambientes.')");
            executeSQL("INSERT INTO tag (id_categoriaplanta, nome, ativa, prioridade, descricao) VALUES " +
                    "(4, 'Novidades em Flores', false, 10, 'Novidades em flores.')");
            executeSQL("INSERT INTO tag (id_categoriaplanta, nome, ativa, prioridade, descricao) VALUES " +
                    "(5, 'Nordeste', true, 0, 'Plantas específicas da região Nordeste do Brasil.')");
            executeSQL("INSERT INTO tag (id_categoriaplanta, nome, ativa, prioridade, descricao) VALUES " +
                    "(5, 'Sudeste', true, 0, 'Plantas específicas da região Sudeste do Brasil.')");
            executeSQL("INSERT INTO tag (id_categoriaplanta, nome, ativa, prioridade, descricao) VALUES " +
                    "(5, 'Sul', true, 0, 'Plantas específicas da região Sul do Brasil.')");
            
            // Plantas
            executeSQL("INSERT INTO planta (id_fornecedor, statusplanta, id_categoriabiologica, nomecomum, nomecientifico, descricao, codigo, precovenda, precocusto, desconto, quantidadedisponivel, quantidadevendido, origem, tempocrescimento, imagens, imagemprincipal) VALUES " +
                    "(1, 2, 1, 'Maçãzeira', 'Malus domestica', 'Árvore frutífera de maçã', '2023P0004', 120, 80, 0.1, 3, 0, 'Região Sul do Brasil', '', '{692a900d-5f4f-4bce-a100-34ed30e406e0.jpeg, 21e47ec3-0dd4-4063-b070-ee346c48b4f4.jpeg}', '692a900d-5f4f-4bce-a100-34ed30e406e0.jpeg')");
            executeSQL("INSERT INTO planta (id_fornecedor, statusplanta, id_categoriabiologica, nomecomum, nomecientifico, descricao, codigo, precovenda, precocusto, desconto, quantidadedisponivel, quantidadevendido, origem, tempocrescimento, imagens, imagemprincipal) VALUES " +
                    "(1, 2, 1, 'Pinheiro', 'Pinus spp.', 'Um belo pinheiro', '2023P0005', 150, 100, 0.1, 8, 0, 'Europa Central e do Norte', '', '{9d646f76-1c78-4aab-b364-99c1aab19ad1.jpg}', '9d646f76-1c78-4aab-b364-99c1aab19ad1.jpg')");
            executeSQL("INSERT INTO planta (id_fornecedor, statusplanta, id_categoriabiologica, nomecomum, nomecientifico, descricao, codigo, precovenda, precocusto, desconto, quantidadedisponivel, quantidadevendido, origem, tempocrescimento, imagens, imagemprincipal) VALUES " +
                    "(1, 2, 2, 'Orquídea', 'Orchidaceae spp.', 'Uma bela orquídea', '2023P0002', 80, 50, 0.05, 5, 0, 'Florestas tropicais da Ásia', '', '{6387263d-e932-4cb1-9e39-5e3a703d3652.jpg}', '6387263d-e932-4cb1-9e39-5e3a703d3652.jpg')");
            executeSQL("INSERT INTO planta (id_fornecedor, statusplanta, id_categoriabiologica, nomecomum, nomecientifico, descricao, codigo, precovenda, precocusto, desconto, quantidadedisponivel, quantidadevendido, origem, tempocrescimento, imagens, imagemprincipal) VALUES " +
                    "(1, 2, 2, 'Rosa do deserto', 'Adenium obesum', 'Uma rosa do deserto', '2023P0001', 100, 60, 0, 2, 0, 'Desertos da África e Arábia', '', '{}', NULL)");
            executeSQL("INSERT INTO planta (id_fornecedor, statusplanta, id_categoriabiologica, nomecomum, nomecientifico, descricao, codigo, precovenda, precocusto, desconto, quantidadedisponivel, quantidadevendido, origem, tempocrescimento, imagens, imagemprincipal) VALUES " +
                    "(1, 2, 2, 'Girassol', 'Helianthus annuus', 'Um girassol vibrante', '2023P0003', 60, 40, 0.1, 10, 0, 'América do Norte e Central', '', '{}', NULL)");
            executeSQL("INSERT INTO planta (id_fornecedor, statusplanta, id_categoriabiologica, nomecomum, nomecientifico, descricao, codigo, precovenda, precocusto, desconto, quantidadedisponivel, quantidadevendido, origem, tempocrescimento, imagens, imagemprincipal) VALUES " +
                    "(1, 2, 1, 'Carnaúba', 'Copernicia prunifera', 'Uma árvore típica do Nordeste brasileiro', '2023P0008', 90, 60, 0.1, 7, 0, 'Região Nordeste do Brasil', '', '{}', NULL)");
            executeSQL("INSERT INTO planta (id_fornecedor, statusplanta, id_categoriabiologica, nomecomum, nomecientifico, descricao, codigo, precovenda, precocusto, desconto, quantidadedisponivel, quantidadevendido, origem, tempocrescimento, imagens, imagemprincipal) VALUES " +
                    "(1, 1, 3, 'Mandacaru', 'Cereus jamacaru', 'Um cacto comum na região Nordeste', '2023P0009', 70, 45, 0, 4, 0, 'Região Nordeste do Brasil', '', '{}', NULL)");
            
            // Relacionamentos planta_tag
            executeSQL("INSERT INTO planta_tag(id_planta, id_tag) VALUES (1, 1)");
            executeSQL("INSERT INTO planta_tag(id_planta, id_tag) VALUES (3, 2)");
            executeSQL("INSERT INTO planta_tag(id_planta, id_tag) VALUES (3, 4)");
            executeSQL("INSERT INTO planta_tag(id_planta, id_tag) VALUES (4, 2)");
            executeSQL("INSERT INTO planta_tag(id_planta, id_tag) VALUES (4, 4)");
            executeSQL("INSERT INTO planta_tag(id_planta, id_tag) VALUES (5, 4)");
            executeSQL("INSERT INTO planta_tag(id_planta, id_tag) VALUES (6, 5)");
            executeSQL("INSERT INTO planta_tag(id_planta, id_tag) VALUES (7, 5)");
            
            // Estados
            executeSQL("INSERT INTO estado(nome, sigla) VALUES ('Tocantins', 'TO')");
            executeSQL("INSERT INTO estado(nome, sigla) VALUES ('Goiás', 'GO')");
            executeSQL("INSERT INTO estado(nome, sigla) VALUES ('Rio de Janeiro', 'RJ')");
            executeSQL("INSERT INTO estado(nome, sigla) VALUES ('São Paulo', 'SP')");
            
            // Cidades
            executeSQL("INSERT INTO cidade(nome, id_estado, frete) VALUES ('Palmas', 1, 10.0)");
            executeSQL("INSERT INTO cidade(nome, id_estado, frete) VALUES ('Pedro Afonso', 1, 15.0)");
            executeSQL("INSERT INTO cidade(nome, id_estado, frete) VALUES ('Cristalina', 2, 20.0)");
            executeSQL("INSERT INTO cidade(nome, id_estado, frete) VALUES ('Rio de Janeiro', 3, 25.0)");
            
            // Usuários
            executeSQL("INSERT INTO usuario (id_telefone, nome, sobrenome, cpf, datanascimento, login, senha) VALUES " +
                    "(3, 'Maria', 'Doe', '11111111111', '2000-03-25', 'maria@gmail.com', 'mGIC/uOHQno3SxCLDKTkWePyuE+8xA13SJCxqKQT8E1N+4GFy3424nwH1ymot2+0ozp9GnnicUmwZs09Fi5HRw==')");
            executeSQL("INSERT INTO usuario (id_telefone, nome, sobrenome, cpf, datanascimento, login, senha) VALUES " +
                    "(null, 'Joao', null, null, null, 'joao@gmail.com', 'uBxMhKJ50d1Kfb+2nN7me98lms/n+1ZVvkzhx7Tx2GRJqqp3TdqgND8RIJALSjrPlg7an9iy+Pt5tOobv9gDMw==')");
            executeSQL("INSERT INTO usuario (id_telefone, nome, sobrenome, cpf, datanascimento, login, senha) VALUES " +
                    "(4, 'Pedro', 'Silva', '22222222222', '1990-03-25', 'pedro@gmail.com', 'PR5BsRIwJ01eCrmZOf2I1w7cz+doyKG85dHk3fvfzTJxx+Xz2nqiWGWumXwGimgOogyj2HTQL1cHnLYLb6Z9xQ==')");
            executeSQL("INSERT INTO usuario (id_telefone, nome, sobrenome, cpf, datanascimento, login, senha) VALUES " +
                    "(null, 'Ana', 'Silva', '33333333333', '1995-03-25', 'ana@gmail.com', 'N+ISheiw0YxOt/9cdGwdjB8zPz1ZCXYKTkrVCUJdFsRmHCnS2UclVL8dK73w1ifv8d1IYXPwZVhqSUfqbuia4A==')");
            executeSQL("INSERT INTO usuario (id_telefone, nome, sobrenome, cpf, datanascimento, login, senha) VALUES " +
                    "(5, 'Lucas', 'Silva', '44444444444', '1998-03-25', 'lucas@gmail.com', 'kj50ILdtWmUUh5DdVYp+wAz8yl8vnU3ZwCkJIigUu75e9M3vuMypKIFbESQ3Z07B7a0Ki8e5csQb09EEH2yBFA==')");
            executeSQL("INSERT INTO usuario (id_telefone, nome, sobrenome, cpf, datanascimento, login, senha) VALUES " +
                    "(null, 'Nelma', 'Souza', '55555555555', '1992-03-25', 'nelma@gmail.com', '+mGQCWJmGiS/rEiEsXa789phw6PrdwKC7JGOviEalw8PlEvyC0VHGJC5VCVRylgVPyH5EgsNfE8+SMqFqjPjrg==')");
            
            // Endereços
            executeSQL("INSERT INTO endereco (id_cidade, nome, cep, rua, bairro, numeroLote, complemento) VALUES " +
                    "(1, 'Casa 1', '77022001', 'Rua Castelo Branco', 'Plano Diretor Norte', '1', 'Perto da maria')");
            executeSQL("INSERT INTO endereco (id_cidade, nome, cep, rua, bairro, numeroLote, complemento) VALUES " +
                    "(1, 'Casa do pai', '77022002', 'Rua Pinheiros', 'Arno', '2', 'Segundo Bairro')");
            executeSQL("INSERT INTO endereco (id_cidade, nome, cep, rua, bairro, numeroLote, complemento) VALUES " +
                    "(2, 'Empresa', '77022003', 'Rua Arnodista', 'Arno 2', '3', 'Segundo Bairro 2')");
            
            // Clientes
            executeSQL("INSERT INTO cliente (id_usuario, carrinho) VALUES (1, '[]')");
            executeSQL("INSERT INTO cliente (id_usuario, carrinho) VALUES (2, '[]')");
            
            // Relacionamentos cliente_endereco
            executeSQL("INSERT INTO cliente_endereco (id_cliente, id_endereco) VALUES (1, 1)");
            executeSQL("INSERT INTO cliente_endereco (id_cliente, id_endereco) VALUES (1, 2)");
            executeSQL("INSERT INTO cliente_endereco (id_cliente, id_endereco) VALUES (1, 3)");
            
            // Admins
            executeSQL("INSERT INTO admin (tipoadmin, id_usuario) VALUES (1, 3)");
            executeSQL("INSERT INTO admin (tipoadmin, id_usuario) VALUES (2, 4)");
            
            // Entregadores
            executeSQL("INSERT INTO entregador (id_usuario, cnh, cnpj) VALUES (5, '123456789', '12345678901234')");
            executeSQL("INSERT INTO entregador (id_usuario, cnh, cnpj) VALUES (6, '223456789', '22345678901234')");
            
            LOGGER.info("=========== Dados iniciais inseridos com sucesso! ===========");
            
        } catch (Exception e) {
            LOGGER.severe("Erro ao inserir dados iniciais: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void executeSQL(String sql) {
        try {
            LOGGER.fine("Executando: " + sql);
            em.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            LOGGER.warning("Erro ao executar SQL: " + sql);
            LOGGER.warning("Mensagem: " + e.getMessage());
        }
    }
}