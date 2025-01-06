package com.example.ec_payment.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.ec_payment.config.FeignClientConfiguration;
import com.example.ec_payment.entites.foreign.order.Order;

@Component
@FeignClient(name = "ec-orders", configuration = FeignClientConfiguration.class)
public interface OrderFeignClient {

    @GetMapping(value = "/orders/{id}")
    ResponseEntity<Order> findById(@PathVariable Long id);

    @PutMapping(value = "/orders/update/{id}/{code}")
    ResponseEntity<Void> update(@PathVariable Long id, @PathVariable Integer code);

}
