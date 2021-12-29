CREATE DATABASE egym;
USE egym;

DROP TABLE IF EXISTS plano;
CREATE TABLE plano(
	plano_id int NOT NULL AUTO_INCREMENT,
    nome varchar(20) NOT NULL,
    preco double NOT NULL,
    descricao varchar(150) NOT NULL,
    PRIMARY KEY(plano_id)
);

DROP TABLE IF EXISTS cliente;
CREATE TABLE cliente(
	id_cliente int NOT NULL AUTO_INCREMENT,
	plano_id int,
    primeiro_nome varchar(25) NOT NULL,
    nome_meio varchar(30) NOT NULL,
	ultimo_nome varchar(40) NOT NULL,
    cpf varchar(11) NOT NULL UNIQUE,
    data_nascimento date NOT NULL,
    telefone varchar(15) NOT NULL,
    PRIMARY KEY(id_cliente),
    FOREIGN KEY(plano_id) REFERENCES plano(plano_id)
);

DROP TABLE IF EXISTS ficha;
CREATE TABLE ficha(
	id_ficha int NOT NULL AUTO_INCREMENT,
    id_cliente int UNIQUE NOT NULL,
    data_inicio date,
    peso_cliente double,
    PRIMARY KEY(id_ficha),
    FOREIGN KEY(id_cliente) REFERENCES cliente(id_cliente)
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS treino;
CREATE TABLE treino(
	id_treino int NOT NULL AUTO_INCREMENT,
    id_ficha int NOT NULL,
    nome varchar(30) NOT NULL,
    tipo varchar(20) NOT NULL,
    PRIMARY KEY(id_treino),
    FOREIGN KEY(id_ficha) REFERENCES ficha(id_ficha)
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS exercicio;
CREATE TABLE exercicio(
	id_exercicio int NOT NULL AUTO_INCREMENT,
    nome varchar(35),
    PRIMARY KEY(id_exercicio)
);

DROP TABLE IF EXISTS treino_tem_exercicio;
CREATE TABLE treino_tem_exercicio(
    id_treino int NOT NULL,
	id_exercicio int NOT NULL,
    PRIMARY KEY(id_treino, id_exercicio),
    FOREIGN KEY(id_treino) REFERENCES treino(id_treino)
    ON DELETE CASCADE,
	FOREIGN KEY(id_exercicio) REFERENCES exercicio(id_exercicio)
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS especificacao;
CREATE TABLE especificacao(
	id_especificacao int NOT NULL AUTO_INCREMENT,
    id_exercicio int NOT NULL,
    id_treino int NOT NULL,
    repeticao int,
    carga int,
    series int,
    descricao varchar(150) NOT NULL,
    PRIMARY KEY(id_especificacao),
    FOREIGN KEY(id_exercicio) REFERENCES exercicio(id_exercicio)
    ON DELETE CASCADE,
    FOREIGN KEY(id_treino) REFERENCES treino(id_treino)
    ON DELETE CASCADE
);