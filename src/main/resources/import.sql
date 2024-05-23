-- new with angular project

-- using: 1, 2, 3
insert into telefone(ddd, numero) values 
  (63, 912345678),
  (62, 912345622),
  (21, 912345621),
  (11, 912345611),
  (11, 912345656);

insert into fornecedor( id_telefone, nome, email, cnpj) values 
  (1, 'Natureza Verde', 'nverde@gmail.com', '11145678000123'),
  (2, 'Plantas PN', 'pnplants@gmail.com', '22234567000123'),
  (null, 'BomVerder', 'bonve@gmail.com', '33345678000123'),
  (null, 'Plnts', 'plants@gmail.com', '44445678000123');

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


INSERT INTO planta_tag(id_planta, id_tag) VALUES
  (1, 1),
  (3, 2),
  (3, 4),
  (4, 2),
  (4, 4),
  (5, 4),
  (6, 5),
  (7,5)
;

-- after A1

INSERT INTO estado(nome, sigla) VALUES 
  ('Tocantins', 'TO'),
  ('Goiás', 'GO'),
  ('Rio de Janeiro', 'RJ'),
  ('São Paulo', 'SP')
;


INSERT INTO cidade(nome, id_estado, frete) VALUES 
  ('Palmas', 1, 10.0),
  ('Pedro Afonso', 1, 15.0),
  ('Cristalina', 2, 20.0),
  ('Rio de Janeiro', 3, 25.0)
;

-- A senha é o nome
-- maria - cliente
-- joao - cliente
-- pedro - admin - owner
-- ana - admin - Employee
-- lucas - entregador
-- nelma - entregador

INSERT INTO usuario (id_telefone, nome, sobrenome, cpf, datanascimento, login, senha ) VALUES
  (3, 'Maria', 'Doe', '11111111111', '2000-03-25', 'maria@gmail.com', 'mGIC/uOHQno3SxCLDKTkWePyuE+8xA13SJCxqKQT8E1N+4GFy3424nwH1ymot2+0ozp9GnnicUmwZs09Fi5HRw=='),
  (null, 'Joao', null, null, null, 'joao@gmail.com', 'uBxMhKJ50d1Kfb+2nN7me98lms/n+1ZVvkzhx7Tx2GRJqqp3TdqgND8RIJALSjrPlg7an9iy+Pt5tOobv9gDMw=='),
  (4, 'Pedro', 'Silva', '22222222222', '1990-03-25', 'pedro@gmail.com', 'PR5BsRIwJ01eCrmZOf2I1w7cz+doyKG85dHk3fvfzTJxx+Xz2nqiWGWumXwGimgOogyj2HTQL1cHnLYLb6Z9xQ=='),
  (null, 'Ana', 'Silva', '33333333333', '1995-03-25', 'ana@gmail.com', 'N+ISheiw0YxOt/9cdGwdjB8zPz1ZCXYKTkrVCUJdFsRmHCnS2UclVL8dK73w1ifv8d1IYXPwZVhqSUfqbuia4A=='),
  (5, 'Lucas', 'Silva', '44444444444', '1998-03-25', 'lucas@gmail.com', 'kj50ILdtWmUUh5DdVYp+wAz8yl8vnU3ZwCkJIigUu75e9M3vuMypKIFbESQ3Z07B7a0Ki8e5csQb09EEH2yBFA=='),
  (null, 'Nelma', 'Souza', '55555555555', '1992-03-25', 'nelma@gmail.com', '+mGQCWJmGiS/rEiEsXa789phw6PrdwKC7JGOviEalw8PlEvyC0VHGJC5VCVRylgVPyH5EgsNfE8+SMqFqjPjrg==');

INSERT INTO endereco (id_cidade, nome, cep, rua, bairro, numeroLote, complemento) VALUES
  (1, 'Casa 1', '77022-001', 'Rua Castelo Branco', 'Plano Diretor Norte', '1', 'Perto da maria'),
  (1, 'Casa do pai', '77022-002', 'Rua Pinheiros', 'Arno', '2', 'Segundo Bairro'),
  (1, 'Empresa', '77022-003', 'Rua Arnodista', 'Arno 2', '3', 'Segundo Bairro 2');

INSERT INTO cliente (id_usuario, carrinho) VALUES
  (1, '[]'),
  (2, '[]');

INSERT INTO cliente_endereco (id_cliente, id_endereco) VALUES
  (1, 1),
  (1, 2),
  (1, 3)
;


// admin
insert into admin (tipoadmin, id_usuario) values
  (1, 3),
  (2, 4);

// entregador
insert into entregador (id_usuario, cnh, cnpj) values
  (5, '123456789', '12345678901234'),
  (6, '223456789', '22345678901234');