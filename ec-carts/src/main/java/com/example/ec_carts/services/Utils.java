package com.example.ec_carts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_carts.feignclients.OAuthFeignClient;

@Service
public class Utils {

    @Autowired
    private OAuthFeignClient authFeignClient;

    public Long getUserId(String token) {
        Long userId = authFeignClient.getLoggedUser(token).getBody().getId();
        return userId;
    }
    
}
