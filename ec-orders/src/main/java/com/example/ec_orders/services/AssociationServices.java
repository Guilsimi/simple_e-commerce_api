package com.example.ec_orders.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_orders.entities.order.Order;
import com.example.ec_orders.entities.order.OrderProductsAssociation;
import com.example.ec_orders.repositories.OrderProductsRepository;
import com.example.ec_orders.services.exceptions.ObjectNotCreatedException;

@Service
public class AssociationServices {

    @Autowired
    private OrderProductsRepository orderProductsRepository;

    @Autowired
    private OrderService orderService;

    public void insertAssociation(OrderProductsAssociation association) {
        try {
            orderProductsRepository.save(association);
        } catch (Exception e) {
            throw new ObjectNotCreatedException("Não foi possível salvar o pedido.");
        }
    }

    public void setProductIdList(Long id) {
        List<OrderProductsAssociation> associationList = orderProductsRepository.findByOrderId(id);
        Order order = orderService.findById(id);
        associationList.forEach(ass -> order.addProductId(ass.getProductId()));
        orderService.update(order);
    }

}
