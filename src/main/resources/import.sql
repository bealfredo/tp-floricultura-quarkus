insert into estado (nome, sigla) values ('Tocantins', 'TO');
insert into estado (nome, sigla) values ('Goiás', 'GO');
insert into estado (nome, sigla) values ('Rio de Janeiro', 'RJ');
insert into estado (nome, sigla) values ('São Paulo', 'SP');

insert into cidade (nome, id_estado) values 
  ('Palmas', 1),
  ('Pedro Afonso', 1),
  ('Cristalina', 2),
  ('Rio de Janeiro', 3);

insert into endereco (id_cidade, bairro, codigo, complemento, numerolote, rua) values 
  (1, 'Plano Diretor Norte', '77022-001', 'Perto da maria', '1', 'Rua Castelo Branco'),
  (1, 'Arno', '77022-002', 'Segundo Bairro', '2', 'Rua Pinheiros'),
  (1, 'Arno 2', '77022-003', 'Segundo Bairro 2', '3', 'Rua Arnodista'),
  (2, 'Centro', '77022-004', 'Centrão', '4', 'Rua da Maria'),
  (2, 'Centro', '77022-004', 'Centrão', '4', 'Rua da Maria'),
  (1, 'Arno 2', '77022-003', 'Segundo Bairro 2', '3', 'Rua Arnodista'),
  (1, 'Arno', '77022-002', 'Segundo Bairro', '2', 'Rua Pinheiros');


INSERT INTO usuario(tipoUsuario, login, nome, sobrenome, cpf, datanascimento, senha) VALUES 
  (3, 'string', 'Test', 'Teste', '11111111111', '2022-03-25T09:45:00', 'bbkbW6fhMoOwDuxoZ0jiAqkHujRJi1k3g4yfYRyTYybON03LFBWxwD7qx2wJmPlJa3hdAinzz9dntHOm9QBoIA=='),
  (1, 'admin', 'Admin', 'Trador', '22222222222', '2022-03-25T09:45:00', '55Lf07wobDs5E1udtQsHKYduh9vJqAJx7O6UElcGSahQp5SCMXL70ZGX7lM2axNPdmBbCqf7+6XuVTepfKH3Nw=='),
  (2, 'maria', 'Maria', 'testadora', '33333333333', '2022-03-25T09:45:00', 'mGIC/uOHQno3SxCLDKTkWePyuE+8xA13SJCxqKQT8E1N+4GFy3424nwH1ymot2+0ozp9GnnicUmwZs09Fi5HRw==');

insert into usuario_endereco (id_endereco, id_usuario) values 
  (1, 1),
  (2, 2),
  (3, 2);

insert into fornecedor (nome, email, telefone, cnpj) values ('Natureza Verde', 'nverde@gmail.com', '63991111111', '11145678000123');
insert into fornecedor (nome, email, telefone, cnpj) values ('Plantas PN', 'pnplants@gmail.com', '63992222222', '22234567000123');
insert into fornecedor (nome, email, telefone, cnpj) values ('BomVerder', 'bonve@gmail.com', '63993333333', '33345678000123');
insert into fornecedor (nome, email, telefone, cnpj) values ('Plnts', 'plants@gmail.com', '63994444444', '44445678000123');

insert into categoriaproduto (nome, descricao) values
  ('Árvores', 'Esta categoria engloba uma variedade de árvores provenientes de diferentes espécies, proporcionando opções diversificadas para seu jardim ou espaço verde.'),
  ('Flores', 'Esta categoria engloba uma variedade de flores para alegrar e decorar diferentes ambientes.'),
  ('Materiais', 'Esta categoria abrange todos os materiais necessários para cuidar e cultivar suas plantas, desde fertilizantes até vasos decorativos, oferecendo recursos essenciais para manter um ambiente verde e saudável.'),
  ('Vasos', 'Esta categoria oferece uma variedade de vasos decorativos para realçar a beleza das suas plantas, proporcionando opções estilizadas para diferentes ambientes.');

insert into tipoproduto (id_categoriaproduto, nome, descricao) VALUES
  (1, 'Árvores frutíferas', 'Árvores que produzem frutos comestíveis, oferecendo uma opção funcional e decorativa para diversos ambientes'),
  (2, 'Flores Decorativas', 'Flores decorativas, ideais para trazer charme e colorido a diversos ambientes.'),
  (3, 'Fertilizante', 'Fertilizante para plantas'),
  (4, 'Vaso Decorativo', 'Vaso decorativo para plantas'),
  (4, 'Vasos de cerâmica', 'Vasos de cerâmica para realçar a beleza das plantas.');

insert into produto(id_fornecedor, nome, descricao, codigo, statusproduto, precovenda, precocusto, desconto, quantidadedisponivel, datadisponivel) values 
  (2, 'Orquídea', 'Uma bela orquídea', '2023P0002', 1, 80, 50, 0.05, 5, '2022-03-15T14:30:00'),
  (1, 'Maçãzeira', 'Árvore frutífera de maçã', '2023P0004', 1, 120, 80, 0.1, 3, '2022-03-25T09:45:00'),
  (2, 'Rosa do deserto', 'Uma rosa do deserto', '2023P0001', 1, 100, 60, 0, 2, '2022-03-10T12:15:50'),
  (2, 'Girassol', 'Um girassol vibrante', '2023P0003', 3, 60, 40, 0.1, 10, '2022-03-20T10:00:00'),
  (3, 'Adubo Orgânico', 'Fertilizante orgânico para plantas', '2023P0005', 1, 40, 25, 0.02, 15, '2022-03-30T13:20:00'),
  (3, 'Vaso Decorativo Floral', 'Vaso decorativo com estampa floral', '2023P0006', 2, 90, 65, 0.08, 8, '2022-04-05T11:10:00'),
  (4, 'Vaso de Cerâmica Branco', 'Vaso de cerâmica branco para plantas', '2023P0007', 3, 60, 45, 0.05, 10, '2022-04-10T16:30:00');

insert into produto_tipoproduto(id_produto, id_tipoproduto) VALUES
  (1, 1),
  (2, 2),
  (3, 2),
  (4, 2),
  (5, 3),
  (6, 4),
  (7, 4),
  (7, 5);

insert into venda (totalvenda, datahora, id_endereco, id_usuario, codigo, chavepix ) values 
  (292, '2023-12-02T03:55:17.2376088', 5, 1, 'VE202312020001' ,'00020126580014BR.GOV.BCB.PIX0136089b6a84-1cbc-474e-956c-c36768a48c025204000053039865802BR5925Alfredo de Souza Aguiar N6009SAO PAULO621405100JVdOWgy4g6304B41D'),
  (108, '2023-12-02T03:57:41.8673783', 6, 1, 'VE202312020002' ,'00020126580014BR.GOV.BCB.PIX0136089b6a84-1cbc-474e-956c-c36768a48c025204000053039865802BR5925Alfredo de Souza Aguiar N6009SAO PAULO621405100JVdOWgy4g6304B41D'),
  (200, '2023-12-02T03:59:41.8673783', 7, 2, 'VE202312020003' ,'00020126580014BR.GOV.BCB.PIX0136089b6a84-1cbc-474e-956c-c36768a48c025204000053039865802BR5925Alfredo de Souza Aguiar N6009SAO PAULO621405100JVdOWgy4g6304B41D');

insert into historicostatus (statusvenda, data, id_venda) values 
  (1, '2023-12-02T03:55:16.2376088', 1), 
  (2, '2023-12-02T03:55:13.2376088', 1), 
  (2, '2023-12-02T03:57:40.8673783', 1), 
  (3, '2023-12-02T03:59:40.8673783', 2); 

UPDATE venda
SET id_laststatus = 1
WHERE id = 1;

UPDATE venda
SET id_laststatus = 3
WHERE id = 2;

UPDATE venda
SET id_laststatus = 4
WHERE id = 3;

insert into itemvenda (preco, quantidade, id_produto, id_venda) values 
  (108, 1, 1, 1),
  (100, 2, 2, 1),
  (108, 1, 2, 2),
  (100, 2, 3, 3);


-- new with angular project

INSERT INTO categoriaplanta (tipocategoria, nome, ativa, prioridade, descricao) VALUES
  (1, 'Árvores', true, 3, 'Esta categoria engloba uma variedade de árvores provenientes de diferentes espécies, proporcionando opções diversificadas para seu jardim ou espaço verde.'),
  (1, 'Flores', false, 2, 'Esta categoria engloba uma variedade de flores para alegrar e decorar diferentes ambientes.'),
  (1, 'Cactos', true, 4, 'Esta categoria engloba uma variedade de cactos, ideais para ambientes secos e com pouca manutenção.'),
  (2, 'Novidades', false, 100, 'Esta categoria abrange todas as novidades em plantas e acessórios para jardim.'),
  (2, 'Plantas por Regiões Brasileiras', true, 1, 'Esta categoria oferece plantas específicas das regiões do Brasil.');


INSERT INTO tag (id_categoriaplanta, nome, ativa, prioridade, descricao) VALUES
  (1, 'Árvores Frutíferas', true, 1, 'Árvores que produzem frutos comestíveis, oferecendo uma opção funcional e decorativa para diversos ambientes.'),
  (2, 'Flores Decorativas', false, 2, 'Flores decorativas, ideais para trazer charme e colorido a diversos ambientes.'),
  (2, 'Flores Azuis', true, 1, 'Flores azuis, ideais para trazer charme e colorido a diversos ambientes.'),
  (4, 'Novidades em Flores', false, 10, 'Novidades em flores.'),
  (5, 'Nordeste', true, 0, 'Plantas específicas da região Nordeste do Brasil.'),
  (5, 'Sudeste', true, 0, 'Plantas específicas da região Sudeste do Brasil.'),
  (5, 'Sul', true, 0, 'Plantas específicas da região Sul do Brasil.');

INSERT INTO planta 
  (id_fornecedor, statusplanta, id_categoriabiologica, nomecomum, nomecientifico, descricao, codigo, precovenda, precocusto, desconto, quantidadedisponivel, quantidadevendido, origem, tempocrescimento, imagens, imagemprincipal) 
VALUES
  (1, 2, 1, 'Maçãzeira', 'Malus domestica', 'Árvore frutífera de maçã', '2023P0004', 120, 80, 0.1, 3, 0, 'Região Sul do Brasil', '', '{692a900d-5f4f-4bce-a100-34ed30e406e0.jpeg, 21e47ec3-0dd4-4063-b070-ee346c48b4f4.jpeg}', '692a900d-5f4f-4bce-a100-34ed30e406e0.jpeg'),
  (1, 1, 1, 'Pinheiro', 'Pinus spp.', 'Um belo pinheiro', '2023P0005', 150, 100, 0.1, 8, 0, 'Europa Central e do Norte', '', '{}', NULL),
  (1, 1, 2, 'Orquídea', 'Orchidaceae spp.', 'Uma bela orquídea', '2023P0002', 80, 50, 0.05, 5, 0, 'Florestas tropicais da Ásia', '', '{}', NULL),
  (1, 2, 2, 'Rosa do deserto', 'Adenium obesum', 'Uma rosa do deserto', '2023P0001', 100, 60, 0, 2, 0, 'Desertos da África e Arábia', '', '{}', NULL),
  (1, 1, 2, 'Girassol', 'Helianthus annuus', 'Um girassol vibrante', '2023P0003', 60, 40, 0.1, 10, 0, 'América do Norte e Central', '', '{}', NULL),
  (1, 1, 1, 'Carnaúba', 'Copernicia prunifera', 'Uma árvore típica do Nordeste brasileiro', '2023P0008', 90, 60, 0.1, 7, 0, 'Região Nordeste do Brasil', '', '{}', NULL),
  (1, 1, 3, 'Mandacaru', 'Cereus jamacaru', 'Um cacto comum na região Nordeste', '2023P0009', 70, 45, 0, 4, 0, 'Região Nordeste do Brasil', '', '{}', NULL);


  insert into planta_tag(id_planta, id_tag) VALUES
  (1, 1),
  (3, 2),
  (3, 4),
  (4, 2),
  (4, 4),
  (5, 4),
  (6, 5),
  (7,5);

  insert into telefone(ddd, numero) VALUES
  (12, 912345678),
  (21, 912345678),
  (43, 912345678),
  (25, 912345678),
  (41, 912345678),
  (42, 912345678),
  (51, 912345678),
  (54, 912345678);