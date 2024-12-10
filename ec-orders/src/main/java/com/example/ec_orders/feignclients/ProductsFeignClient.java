package com.example.ec_orders.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.ec_orders.config.FeignClientConfiguration;
import com.example.ec_orders.entities.foreign.product.Product;

@FeignClient(name = "ec-product", configuration = FeignClientConfiguration.class)
public interface ProductsFeignClient {

    @GetMapping(value = "/products/{id}")
    ResponseEntity<Product> findById(@PathVariable Long id);

    @GetMapping(value = "/products")
    ResponseEntity<List<Product>> findAll();

    @PutMapping(value = "/products/decrease/{id}/{quantity}")
    void updateQuantity(@PathVariable Long id, @PathVariable Integer quantity);

}
