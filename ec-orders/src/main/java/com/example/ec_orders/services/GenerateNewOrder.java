package com.example.ec_orders.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_orders.entities.enums.Status;
import com.example.ec_orders.entities.order.Order;
import com.example.ec_orders.entities.order.OrderProductsAssociation;

@Service
public class GenerateNewOrder {

    @Autowired
    private Utils utils;

    @Autowired
    private OrderService orderService;

    public Order generateOrder(List<Long> products, List<Integer> quantity, String token) {
        Double total = utils.total(products, quantity);
        Long userId = utils.getUserId(token);
        Order newOrder = new Order(null, null, new Date(), total, Status.PENDENTE, userId);
        orderService.insert(newOrder);

        for (Long l : products) {
            OrderProductsAssociation association = new OrderProductsAssociation(null, newOrder.getId(), l);
            orderService.insertAssociation(association);
        }

        orderService.setProductIdList(newOrder.getId());

        return newOrder;
    }

}
