package com.example.ec_user.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "ec-cart")
public interface CartFeignClient {

    @PostMapping("/insert/new/cart/{id}")
    ResponseEntity<Void> insert(@PathVariable Long id);

}
