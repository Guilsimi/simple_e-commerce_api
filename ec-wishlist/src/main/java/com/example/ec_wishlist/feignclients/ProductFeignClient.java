package com.example.ec_wishlist.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ec_wishlist.config.FeignClientConfiguration;
import com.example.ec_wishlist.entities.foreign.product.Product;


@FeignClient(name = "ec-product",  configuration = FeignClientConfiguration.class)
public interface ProductFeignClient {

    @GetMapping(value = "/products/{id}")
    ResponseEntity<Product> findById(@PathVariable Long id);

}
