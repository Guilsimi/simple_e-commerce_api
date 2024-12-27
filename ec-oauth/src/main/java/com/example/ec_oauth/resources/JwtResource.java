package com.example.ec_oauth.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_oauth.entities.UserEntity;
import com.example.ec_oauth.feignclient.UsersFeignClient;
import com.example.ec_oauth.resources.exceptions.ResourceNotFoundException;
import com.example.ec_oauth.services.JwtServices;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping("/jwt")
public class JwtResource {

    @Autowired
    private JwtServices jwtService;

    @Autowired
    private UsersFeignClient userFeignClient;

    @GetMapping(value = "/user")
    public ResponseEntity<UserEntity> getLoggedUser(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.replace("Bearer ", "");
            String email = jwtService.getClaimFromToken(token, "sub");
            UserEntity user = userFeignClient.findByEmail(email).getBody();

            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao identiicar a sessão. Tente novamente.");
        }
    }

}
