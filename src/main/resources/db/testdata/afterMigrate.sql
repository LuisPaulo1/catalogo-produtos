delete from product;

alter table product auto_increment = 1;

INSERT INTO product (id, name, description, price) VALUES (1, 'The Lord of the Rings', 'Descrição 1.', 90.5);
INSERT INTO product (id, name, description, price) VALUES (2, 'Smart TV', 'Descrição 2.', 590.0);
INSERT INTO product (id, name, description, price) VALUES (3, 'Macbook Pro', 'Descrição 3.', 432.0);
INSERT INTO product (id, name, description, price) VALUES (4, 'PC Gamer', 'Descrição 4.', 600.0);
INSERT INTO product (id, name, description, price) VALUES (5, 'Rails for Dummies', 'Descrição 5.', 705.80);