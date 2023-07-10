CREATE TABLE IF NOT EXISTS category (
	cod BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO category (name) VALUES ('Lazer');
INSERT INTO category (name) VALUES ('Alimentação');
INSERT INTO category (name) VALUES ('Supermercado');
INSERT INTO category (name) VALUES ('Farmácia');
INSERT INTO category (name) VALUES ('Outros');