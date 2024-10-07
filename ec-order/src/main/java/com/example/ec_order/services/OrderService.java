package com.example.ec_order.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_order.entites.Order;
import com.example.ec_order.repositories.OrderRepository;
import com.example.ec_order.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    public void insert(Order order) {
        orderRepository.save(order);
    }
}
