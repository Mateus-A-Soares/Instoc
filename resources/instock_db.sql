DROP DATABASE IF EXISTS instock_db;
CREATE DATABASE instock_db;
USE instock_db;

CREATE TABLE usuario(
	id BIGINT SIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    email VARCHAR(125) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    permissao INT(11) NOT NULL,
    senha BLOB NOT NULL,
    ativo BOOLEAN NOT NULL,
    primary key(id)
);

DELIMITER $$
CREATE
		TRIGGER trg_usuario_insert BEFORE INSERT
		ON usuario
		FOR EACH ROW BEGIN
			SET NEW.senha = md5(NEW.senha);
	END$$
DELIMITER ;

DELIMITER $$
CREATE
		TRIGGER trg_usuario_update BEFORE UPDATE
		ON usuario
		FOR EACH ROW BEGIN
			SET NEW.senha = md5(NEW.senha);
	END$$
DELIMITER ;

CREATE TABLE ambiente(
	id BIGINT SIGNED NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(75) NOT NULL,
    cadastrante_id BIGINT SIGNED NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(cadastrante_id) REFERENCES usuario(id)
);

CREATE TABLE tipo_item(
	id BIGINT SIGNED NOT NULL AUTO_INCREMENT,
    nome VARCHAR(75) NOT NULL,
    cadastrante_id BIGINT SIGNED NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(cadastrante_id) REFERENCES usuario(id)
);

CREATE TABLE tipo_item_tag(
	id BIGINT SIGNED NOT NULL AUTO_INCREMENT,
    cabecalho VARCHAR(50) NOT NULL,
    corpo VARCHAR(50),
    tipo VARCHAR(50),
    tipo_item_id BIGINT SIGNED NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(tipo_item_id) REFERENCES tipo_item(id)
);

CREATE TABLE item(
	id BIGINT SIGNED NOT NULL AUTO_INCREMENT,
    tipo_id BIGINT SIGNED NOT NULL,
    cadastrante_id BIGINT SIGNED NOT NULL,
    ambiente_atual_id BIGINT SIGNED NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(tipo_id) REFERENCES tipo_item(id),
    FOREIGN KEY(cadastrante_id) REFERENCES usuario(id),
    FOREIGN KEY(ambiente_atual_id) REFERENCES ambiente(id)
);

CREATE TABLE movimentacao(
	id BIGINT SIGNED NOT NULL AUTO_INCREMENT,
    data_movimentacao DATETIME NOT NULL,
    item_movimentado_id BIGINT SIGNED NOT NULL,
    ambiente_anterior_id BIGINT SIGNED NOT NULL,
    ambiente_posterior_id BIGINT SIGNED NOT NULL,
    movimentador_id BIGINT SIGNED NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(item_movimentado_id) REFERENCES item(id),
    FOREIGN KEY(ambiente_anterior_id) REFERENCES ambiente(id),
    FOREIGN KEY(ambiente_posterior_id) REFERENCES ambiente(id),
    FOREIGN KEY(movimentador_id) REFERENCES usuario(id)
);

DELIMITER $$
CREATE
		TRIGGER trg_movimentacao_insert AFTER INSERT
		ON movimentacao
		FOR EACH ROW BEGIN
			UPDATE item SET ambiente_atual_id  = NEW.ambiente_posterior_id WHERE id = NEW.item_movimentado_id;
	END$$
DELIMITER ;