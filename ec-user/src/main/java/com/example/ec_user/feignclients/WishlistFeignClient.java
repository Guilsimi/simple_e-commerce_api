package com.example.ec_user.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ec_user.config.FeignClientConfiguration;

@FeignClient(name = "ec-wishlist", configuration = FeignClientConfiguration.class)
public interface WishlistFeignClient {

    @PostMapping("/insert/new/wishlist/{id}")
    ResponseEntity<Void> insert(@PathVariable Long id);

}
