package com.example.ec_payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_payment.feignclients.OAuthFeignClient;
import com.example.ec_payment.services.exceptions.ResourceNotFoundException;

import feign.FeignException;

@Service
public class Utils {

    @Autowired
    private OAuthFeignClient authFeignClient;

    public String getUserEmail(String token) {
        try {
            String userEmail = authFeignClient.getLoggedUser(token).getBody().getEmail();
        return userEmail;
        } catch (FeignException e) {
            throw new ResourceNotFoundException("Erro ao realizar a comunicação com o serviço de autorização");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao encontrar informações do usuário");
        }
        
    }

}
