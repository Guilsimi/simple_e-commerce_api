package com.example.ec_order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ec_order.entites.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
