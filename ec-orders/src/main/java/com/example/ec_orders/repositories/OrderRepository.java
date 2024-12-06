package com.example.ec_orders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ec_orders.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
