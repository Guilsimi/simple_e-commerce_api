package com.example.ec_order.config.feignconfig;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.ec_order.entites.Product;
import com.example.ec_order.feignclient.ProductFeignClient;

@Component
public class FeignFallback implements ProductFeignClient {

    @Override
    public ResponseEntity<Product> findById(Long id) {
        Product fallbackProduct = new Product(null, null, null, null, null, null, null);
        fallbackProduct.setMessage("Try again!");
        return ResponseEntity.status(503).body(fallbackProduct);
    }

}
