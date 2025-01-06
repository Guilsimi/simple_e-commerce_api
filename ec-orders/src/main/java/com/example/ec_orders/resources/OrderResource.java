package com.example.ec_orders.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_orders.entities.enums.Status;
import com.example.ec_orders.entities.order.Order;
import com.example.ec_orders.services.OrderService;
import com.example.ec_orders.services.Utils;
import com.example.ec_orders.services.exceptions.ResourceNotFoundException;

import jakarta.annotation.Resource;

@RefreshScope
@Resource
@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @Autowired
    private Utils utils;

    @GetMapping
    public ResponseEntity<List<Order>> findAll(@RequestHeader("Authorization") String bearerToken) {
        Long userId = utils.getUserId(bearerToken);
        List<Order> userOrders = orderService.findByUserId(userId);

        return ResponseEntity.ok().body(userOrders);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @PutMapping(value = "update/{id}/{code}")
    public ResponseEntity<Void> update(@PathVariable Long id, @PathVariable Integer code) {
        try {
            Order order = orderService.findById(id);
            order.setStatus(Status.valueOf(code));
            orderService.update(order);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao encontrar o pedido " + e.getMessage());
        }

    }

}
