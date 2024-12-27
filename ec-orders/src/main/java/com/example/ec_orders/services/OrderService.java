package com.example.ec_orders.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_orders.entities.order.Order;
import com.example.ec_orders.repositories.OrderRepository;
import com.example.ec_orders.services.exceptions.ObjectNotCreatedException;
import com.example.ec_orders.services.exceptions.ResourceNotFoundException;
import com.example.ec_orders.services.exceptions.ResourceNotUpdatedException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));
    }

    public List<Order> findByUserId(Long id) {
        try {
            List<Order> userOrders = orderRepository.findByUserId(id);
            return userOrders;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Pedidos não encontrados.");
        }
    }

    public void insert(Order order) {
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            throw new ObjectNotCreatedException("Não foi possível criar o pedido no momento.");
        }
    }

    public void update(Order orderObj) {
        try {
            Order newOrder = findById(orderObj.getId());
            updateData(newOrder, orderObj);
            orderRepository.save(newOrder);
        } catch (Exception e) {
            throw new ResourceNotUpdatedException("Erro ao atualizar o pedido");
        }

    }

    private void updateData(Order newOrder, Order objOrder) {
        newOrder.setDate(
                objOrder.getDate() != null ? objOrder.getDate() : newOrder.getDate());
        newOrder.setProductId(
                objOrder.getProductId() != null ? objOrder.getProductId() : newOrder.getProductId());
        newOrder.setStatus(
                objOrder.getStatus() != null ? objOrder.getStatus() : newOrder.getStatus());
        newOrder.setTotal(
                objOrder.getTotal() != null ? objOrder.getTotal() : newOrder.getTotal());
        newOrder.setUserId(
                objOrder.getUserId() != null ? objOrder.getUserId() : newOrder.getUserId());
    }

}
