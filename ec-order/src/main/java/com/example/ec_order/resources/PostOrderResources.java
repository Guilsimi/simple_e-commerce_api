package com.example.ec_order.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_order.entites.Order;
import com.example.ec_order.entites.dto.OrderDTO;
import com.example.ec_order.services.OrderService;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/orders")
public class PostOrderResources {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody OrderDTO orderdto) {
        Order order = fromDTO(orderdto);
        orderService.insert(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Order fromDTO(OrderDTO obj) {
        return new Order(obj.getId(), obj.getProductName(), obj.getDate(), obj.getTotal(), obj.getStatus());
    }
}
