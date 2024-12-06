package com.example.ec_orders.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_orders.entities.Order;
import com.example.ec_orders.entities.foreign.user.UserEntity;
import com.example.ec_orders.feignclients.OAuthFeignClient;
import com.example.ec_orders.services.OrderService;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/orders")
public class OrdersResource {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OAuthFeignClient oAuthFeignClient;

    @GetMapping
    public ResponseEntity<List<Order>> findAll(@RequestHeader("Authorization") String bearerToken) {
        UserEntity user = oAuthFeignClient.getLoggedUser(bearerToken).getBody();
        List<Order> allOrders = orderService.findAll();
        List<Order> userOrders = new ArrayList<>();

        for(Order o : allOrders) {
            if(o.getUserId().equals(user.getId())) {
                userOrders.add(o);
            }
        }

        return ResponseEntity.ok().body(userOrders);
    }

}
