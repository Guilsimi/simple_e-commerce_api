create table tb_order (status integer not null, total float(53), date timestamp(6), id bigint generated by default as identity, user_id bigint, product_id bigint array, primary key (id));
create table tb_order_product (id bigint generated by default as identity, order_id bigint not null, product_id bigint not null, primary key (id));
