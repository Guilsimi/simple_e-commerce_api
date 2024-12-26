package com.example.ec_payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_payment.feignclients.OAuthFeignClient;

@Service
public class Utils {

    @Autowired
    private OAuthFeignClient authFeignClient;

    public String getUserEmail(String token) {
        String userEmail = authFeignClient.getLoggedUser(token).getBody().getEmail();
        return userEmail;
    }

}
