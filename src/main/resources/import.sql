insert into estado (nome, sigla) values ('Tocantins', 'TO');
insert into estado (nome, sigla) values ('Goiás', 'GO');
insert into estado (nome, sigla) values ('Rio de Janeiro', 'RJ');
insert into estado (nome, sigla) values ('São Paulo', 'SP');

INSERT INTO usuario(login, nome, sobrenome, datanascimento, senha) VALUES 
  ('string', 'Usuario', 'Teste', '2022-03-25T09:45:00', 'bbkbW6fhMoOwDuxoZ0jiAqkHujRJi1k3g4yfYRyTYybON03LFBWxwD7qx2wJmPlJa3hdAinzz9dntHOm9QBoIA=='),
  ('maria', 'maria', 'testadora', '2022-03-25T09:45:00', 'mGIC/uOHQno3SxCLDKTkWePyuE+8xA13SJCxqKQT8E1N+4GFy3424nwH1ymot2+0ozp9GnnicUmwZs09Fi5HRw==');

insert into fornecedor (nome, email, telefone, cnpj) values ('Natureza Verde', 'nverde@gmail.com', '63991111111', '1114567890123');
insert into fornecedor (nome, email, telefone, cnpj) values ('Plantas PN', 'pnplants@gmail.com', '63992222222', '22234567890123');
insert into fornecedor (nome, email, telefone, cnpj) values ('BomVerder', 'bonve@gmail.com', '63993333333', '3334567890123');
insert into fornecedor (nome, email, telefone, cnpj) values ('Plnts', 'plants@gmail.com', '63994444444', '4444567890123');

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
  (1, 'Maçãzeira', 'Árvore frutífera de maçã', '2023P0004', 1, 120, 80, 0.1, 3, '2022-03-25T09:45:00'),
  (2, 'Rosa do deserto', 'Uma rosa do deserto', '2023P0001', 1, 100, 60, 0, 2, '2022-03-10T12:15:50'),
  (2, 'Orquídea', 'Uma bela orquídea', '2023P0002', 2, 80, 50, 0.05, 5, '2022-03-15T14:30:00'),
  (2, 'Girassol', 'Um girassol vibrante', '2023P0003', 3, 60, 40, 0.1, 10, '2022-03-20T10:00:00'),
  (3, 'Adubo Orgânico', 'Fertilizante orgânico para plantas', '2023P0005', 1, 40, 25, 0.02, 15, '2022-03-30T13:20:00'),
  (3, 'Vaso Decorativo Floral', 'Vaso decorativo com estampa floral', '2023P0006', 1, 90, 65, 0.08, 8, '2022-04-05T11:10:00'),
  (4, 'Vaso de Cerâmica Branco', 'Vaso de cerâmica branco para plantas', '2023P0007', 1, 60, 45, 0.05, 10, '2022-04-10T16:30:00');

insert into produto_tipoproduto(id_produto, id_tipoproduto) VALUES
  (1, 1),
  (2, 2),
  (3, 2),
  (4, 2),
  (5, 3),
  (6, 4),
  (7, 4),
  (7, 5);