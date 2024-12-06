package com.example.ec_oauth.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ec_oauth.entities.UserEntity;

@Component
@FeignClient(name = "ec-user", path = "/user")
public interface UsersFeignClient {

    @GetMapping(value = "/search")
    ResponseEntity<UserEntity> findByEmail(@RequestParam String email);

}
