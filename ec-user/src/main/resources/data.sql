INSERT INTO tb_role (role)
VALUES ('ROLE_ADMINISTRADOR');
INSERT INTO tb_role (role)
VALUES ('ROLE_CLIENTE');

INSERT INTO tb_user (address, email, name, password, phone) 
VALUES ('Rua do ADM, 7000', 'adm@gmail.com', 'Admnistrador', '$2a$10$ZGJHAX0zkD2X5AxjuLVE2u3IxUcIJ.tOr9mYJ6ONLocbrMh9E5k6O', '33030030');
INSERT INTO tb_user (address, email, name, password, phone) 
VALUES ('Rua do Cliente, 20', 'clientedaloja@gmail.com', 'Cliente', '$2a$10$Vm267BLupKjpWg5xxvsmxe.ogjuP71mXrzwQd1EcXSNMQNo/fU8X6', '990990990');

INSERT INTO tb_user_role (user_id, role_id)
VALUES(1, 1);
INSERT INTO tb_user_role (user_id, role_id)
VALUES(2, 2);