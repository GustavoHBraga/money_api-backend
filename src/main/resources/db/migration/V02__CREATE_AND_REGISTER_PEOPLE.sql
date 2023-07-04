CREATE TABLE people (
	cod BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	isActive BOOLEAN NOT NULL,
	address VARCHAR(100) NOT NULL,
	number INT NOT NULL,
	complement VARCHAR(50),
	district VARCHAR(50) NOT NULL,
	cep VARCHAR(9) NOT NULL,
	city VARCHAR(50) NOT NULL,
	state VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO people (name,isActive,address,number,complement,district,cep,city,state)
	VALUES ('Gustavo Braga', true, 'Rua das palmeiras', 145, 'Apartamento 95', 'IAPO', 'OSASCO', 'SAO PAULO');

INSERT INTO people (name,isActive,address,number,complement,district,cep,city,state)
	VALUES ('Carlos albert', false, 'Rua das palmeiras verdes', 541, 'CASA 1', 'ALPHAVILLE', 'BARUERI', 'SAO PAULO');
	
	