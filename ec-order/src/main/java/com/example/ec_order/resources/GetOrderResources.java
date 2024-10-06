package com.example.ec_order.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_order.entites.Order;
import com.example.ec_order.services.GetOrderAndProducts;
import com.example.ec_order.services.OrderService;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/orders")
public class GetOrderResources {

    @Autowired
    OrderService orderService;

    @Autowired
    GetOrderAndProducts getOrderAndProducts;

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        List<Order> orderList = orderService.findAll();
        return ResponseEntity.ok().body(orderList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping(value = "/{productsId}/quantity/{quantity}")
    public ResponseEntity<Order> getOrder(@PathVariable Long productsId, @PathVariable Integer quantity) {
        Order order = getOrderAndProducts.getOrder(productsId, quantity);
        return ResponseEntity.ok(order);
    }
}
