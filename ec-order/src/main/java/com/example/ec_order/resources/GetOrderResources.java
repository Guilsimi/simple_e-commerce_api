package com.example.ec_order.resources;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_order.entites.Order;
import com.example.ec_order.entites.enums.Status;
import com.example.ec_order.services.GetOrderAndProducts;
import com.example.ec_order.services.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/orders")
public class GetOrderResources {

    @Value("${test.config}")
    private String testConfig;

    @Autowired
    OrderService orderService;

    @Autowired
    GetOrderAndProducts getOrderAndProducts;

    @GetMapping(value = "/configs")
    public ResponseEntity<Void> getConfigs() {
        Logger logger = LoggerFactory.getLogger(GetOrderResources.class);
        logger.info(testConfig);
        return ResponseEntity.noContent().build();
    }

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

    @CircuitBreaker(fallbackMethod = "getOrderFallback", name = "getOrderUsingProductIdCircuitBreaker")
    @GetMapping(value = "/{productsId}/quantity/{quantity}")
    public ResponseEntity<Order> getOrder(@PathVariable Long productsId, @PathVariable Integer quantity) {
        Order order = getOrderAndProducts.getOrder(productsId, quantity);
        return ResponseEntity.ok(order);
    }

    public ResponseEntity<Order> getOrderFallback(Long productsId, Integer quantity, Throwable throwable) {
        Order order = new Order(0L, "?", new Date(0), 0.0, Status.ERROR_CODE);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Service is up");
    }
    
}
