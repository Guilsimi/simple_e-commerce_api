package com.example.ec_order.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ec_order.config.feignconfig.FeignConfig;
import com.example.ec_order.config.feignconfig.FeignFallback;
import com.example.ec_order.entites.Product;

@Component
@FeignClient(name = "ec-products", path = "/products", configuration = FeignConfig.class, fallback = FeignFallback.class)
public interface ProductFeignClient {

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id);

}
