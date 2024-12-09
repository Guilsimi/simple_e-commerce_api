package com.example.ec_orders.resources.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_orders.entities.order.Order;
import com.example.ec_orders.services.GenerateNewOrder;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/orders")
public class GenerateNewOrderController {

    @Autowired
    private GenerateNewOrder generateNewOrder;

    @PostMapping(value = "/products/{productsId}/quantity/{quantity}")
    public ResponseEntity<Order> generateOrder(
            @PathVariable List<Long> productsId,
            @PathVariable List<Integer> quantity,
            @RequestHeader("Authorization") String bearerToken) {
        Order order = generateNewOrder.generateOrder(productsId, quantity, bearerToken);
        return ResponseEntity.ok().body(order);
    }

}
