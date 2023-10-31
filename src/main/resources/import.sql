insert into estado (nome, sigla) values ('Tocantins', 'TO');
insert into estado (nome, sigla) values ('Goiás', 'GO');
insert into estado (nome, sigla) values ('Rio de Janeiro', 'RJ');
insert into estado (nome, sigla) values ('São Paulo', 'SP');

insert into fornecedor (nome, email, telefone, cnpj) values ('Natureza Verde', 'nverde@gmail.com', '63991111111', '1114567890123');
insert into fornecedor (nome, email, telefone, cnpj) values ('Plantas PN', 'pnplants@gmail.com', '63992222222', '22234567890123');
insert into fornecedor (nome, email, telefone, cnpj) values ('BomVerder', 'bonve@gmail.com', '63993333333', '3334567890123');
insert into fornecedor (nome, email, telefone, cnpj) values ('Plnts', 'plants@gmail.com', '63994444444', '4444567890123');

insert into categoriaproduto (nome, descricao) values ('Árvores', 'Árvores de diferentes espécies');
insert into categoriaproduto (nome, descricao) values ('Materiais', 'Todos os materiais para cuidar das suas plantas');

insert into tipoproduto (nome, descricao, id_categoriaproduto) values ('Árvores aquáticas', 'Árvores que ficam na água', 1);
insert into tipoproduto (nome, descricao, id_categoriaproduto) values ('Árvores frutíferas', 'Árvores que dão frutos', 1);
