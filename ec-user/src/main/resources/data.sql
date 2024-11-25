INSERT INTO tb_role (role)
VALUES ('ROLE_ADMINISTRADOR');
INSERT INTO tb_role (role)
VALUES ('ROLE_CLIENTE');

INSERT INTO tb_user (address, email, name, password, phone) 
VALUES ('Rua do ADM, 7000', 'adm@gmail.com', 'Admnistrador', '$2a$10$EN3rImh1Yps9hEHXuDYJBOIh4ZEQOL3QKIfiBI3hmqnbtpGoQMRt.', '33030030');
INSERT INTO tb_user (address, email, name, password, phone) 
VALUES ('Rua do Cliente, 20', 'user@gmail.com', 'Cliente', '$2a$10$cKv2yYwqlejyH.KbQlVE1ehYL8xCUTMEIPvn3nT6ca5XFiwKdX6OS', '990990990');

INSERT INTO tb_user_role (user_id, role_id)
VALUES(1, 1);
INSERT INTO tb_user_role (user_id, role_id)
VALUES(2, 2);