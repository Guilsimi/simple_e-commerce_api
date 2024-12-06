package com.example.ec_orders.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_orders.entities.Order;
import com.example.ec_orders.repositories.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

}
