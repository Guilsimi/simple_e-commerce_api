package com.example.ec_payment.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.ec_payment.config.FeignClientConfiguration;
import com.example.ec_payment.entites.foreign.user.UserEntity;

@Component
@FeignClient(name = "ec-oauth", configuration = FeignClientConfiguration.class)
public interface OAuthFeignClient {

    @GetMapping(value = "/jwt/user")
    public ResponseEntity<UserEntity> getLoggedUser(@RequestHeader("Authorization") String bearerToken);

}
