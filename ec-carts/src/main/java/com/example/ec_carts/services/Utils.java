package com.example.ec_carts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_carts.feignclients.OAuthFeignClient;
import com.example.ec_carts.services.exceptions.ResourceNotFoundException;

import feign.FeignException;

@Service
public class Utils {

    @Autowired
    private OAuthFeignClient authFeignClient;

    public Long getUserId(String token) {
        try {
            Long userId = authFeignClient.getLoggedUser(token).getBody().getId();
            return userId;
        } catch (FeignException e) {
            throw new ResourceNotFoundException("Erro de comunicação com o serviço de autorização");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao localizar a sessão");
        }
    }

}
