package com.example.ec_orders.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ec_orders.entities.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long id);
    
}
