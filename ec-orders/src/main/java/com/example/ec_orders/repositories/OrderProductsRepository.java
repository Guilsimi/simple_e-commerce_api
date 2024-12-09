package com.example.ec_orders.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ec_orders.entities.order.OrderProductsAssociation;

public interface OrderProductsRepository extends JpaRepository<OrderProductsAssociation, Long> {

    List<OrderProductsAssociation> findByOrderId(Long id);

}
