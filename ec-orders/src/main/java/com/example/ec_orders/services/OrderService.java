package com.example.ec_orders.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_orders.entities.order.Order;
import com.example.ec_orders.entities.order.OrderProductsAssociation;
import com.example.ec_orders.repositories.OrderProductsRepository;
import com.example.ec_orders.repositories.OrderRepository;
import com.example.ec_orders.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductsRepository orderProductsRepository;

    public List<Order> findAll() {
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }

    public List<Order> findByUserId(Long id) {
        List<Order> userOrders = orderRepository.findByUserId(id);
        return userOrders;
    }

    public void insert(Order order) {
        orderRepository.save(order);
    }

    public void insertAssociation(OrderProductsAssociation association) {
        orderProductsRepository.save(association);
    }

    public void setProductIdList(Long id) {
        List<OrderProductsAssociation> associationList = orderProductsRepository.findByOrderId(id);
        Order order = findById(id);
        associationList.forEach(ass -> order.addProductId(ass.getProductId()));
        update(order);
    }

    private void update(Order orderObj) {
        Order newOrder = findById(orderObj.getId());
        updateData(newOrder, orderObj);
        orderRepository.save(newOrder);
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
