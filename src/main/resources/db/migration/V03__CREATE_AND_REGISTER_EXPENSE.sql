CREATE TABLE IF NOT EXISTS expense (
cod BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
description VARCHAR(50) NOT NULL,
due_date DATE NOT NULL,
payment_date DATE,
amount DECIMAL(10,2) NOT NULL,
observation VARCHAR(100),
type VARCHAR(20) NOT NULL,
cod_category BIGINT(20) NOT NULL,
cod_person BIGINT(20) NOT NULL,
FOREIGN KEY (cod_category) REFERENCES category(cod),
FOREIGN KEY (cod_person) REFERENCES person(cod)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Salário mensal', '2017-06-10', null, 6500.00, 'Distribuição de lucros', 'REVENUE', 1, 1);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Bahamas', '2017-02-10', '2017-02-10', 100.32, null, 'COST', 2, 2);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Top Club', '2017-06-10', null, 120, null, 'REVENUE', 3, 2);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('CEMIG', '2017-02-10', '2017-02-10', 110.44, 'Geração', 'REVENUE', 3, 1);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('DMAE', '2017-06-10', null, 200.30, null, 'COST', 3, 1);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Extra', '2017-03-10', '2017-03-10', 1010.32, null, 'REVENUE', 4, 1);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Bahamas', '2017-06-10', null, 500, null, 'REVENUE', 1, 1);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Top Club', '2017-03-10', '2017-03-10', 400.32, null, 'COST', 4, 2);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Despachante', '2017-06-10', null, 123.64, 'Multas', 'COST', 3, 1);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Pneus', '2017-04-10', '2017-04-10', 665.33, null, 'REVENUE', 5, 2);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Café', '2017-06-10', null, 8.32, null, 'COST', 1, 1);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Eletrônicos', '2017-04-10', '2017-04-10', 2100.32, null, 'COST', 5, 1);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Instrumentos', '2017-06-10', null, 1040.32, null, 'COST', 4, 1);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Café', '2017-04-10', '2017-04-10', 4.32, null, 'COST', 4, 2);
INSERT INTO expense (description, due_date, payment_date, amount, observation, type, cod_category, cod_person) values ('Lanche', '2017-06-10', null, 10.20, null, 'COST', 4, 1);

