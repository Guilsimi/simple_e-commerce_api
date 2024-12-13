package com.example.ec_user.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_user.entities.Role;
import com.example.ec_user.entities.User;
import com.example.ec_user.entities.dto.UserDTO;
import com.example.ec_user.feignclients.CartFeignClient;
import com.example.ec_user.services.UserServices;
import com.example.ec_user.services.exceptions.ResourceNotFoundException;

import jakarta.annotation.Resource;

@RestController
@Resource
@RequestMapping(value = "/register")
public class RegisterUserResource {

    @Autowired
    private UserServices services;

    @Autowired
    private CartFeignClient cartFeignClient;

    @PostMapping
    public ResponseEntity<Void> createNewUser(
            @RequestBody UserDTO userDto) {
        try {
            User user = fromDTO(userDto);
            User createdUser = services.insert(user);
            Role role = new Role(2L, "'ROLE_CLIENTE'");
            createdUser.getRoles().add(role);
            services.update(createdUser);

            try {
                cartFeignClient.insert(createdUser.getId());
            } catch (Exception e) {
                throw new ResourceNotFoundException("Erro ao criar o carrinho");
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erro ao criar o usuário");
        }
    }

    private User fromDTO(UserDTO objUser) {
        return new User(
                objUser.getId(),
                objUser.getName(),
                objUser.getEmail(),
                objUser.getPassword(),
                objUser.getAddress(),
                objUser.getPhone());
    }
}
