INSERT INTO tb_order (status, total, date, user_id)
VALUES (1, 20.0, CURRENT_TIMESTAMP, 1);

INSERT INTO tb_order (status, total, date, user_id)
VALUES (1, 20.0, CURRENT_TIMESTAMP, 1);

INSERT INTO tb_order_product (order_id, product_id) 
VALUES (1, 1);

INSERT INTO tb_order_product (order_id, product_id) 
VALUES (1, 2);

INSERT INTO tb_order_product (order_id, product_id) 
VALUES (2, 1);