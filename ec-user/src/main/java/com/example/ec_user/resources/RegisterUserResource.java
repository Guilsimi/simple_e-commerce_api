package com.example.ec_user.resources;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_user.entities.User;
import com.example.ec_user.entities.dto.UserDTO;
import com.example.ec_user.entities.records.RegisterResponse;
import com.example.ec_user.feignclients.CartFeignClient;
import com.example.ec_user.feignclients.WishlistFeignClient;
import com.example.ec_user.services.UserServices;
import com.example.ec_user.services.exceptions.ObjectNotCreatedException;

import feign.FeignException.FeignClientException;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

@RestController
@Resource
@RequestMapping(value = "/register")
public class RegisterUserResource {

    @Autowired
    private UserServices services;

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private WishlistFeignClient wishlistFeignClient;

    @PostMapping
    @Transactional
    public ResponseEntity<RegisterResponse> createNewUser(
            @Valid @RequestBody UserDTO userDto) {

        if (services.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RegisterResponse(
                    HttpStatus.CONFLICT.value(),
                    Arrays.asList(
                            "An account with the email already exists",
                            "Try to login in existing account or register with other email")));
        }

        try {
            User user = fromDTO(userDto);
            User createdUser = services.insert(user);
            createUserResources(createdUser.getId());
            services.update(createdUser);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ObjectNotCreatedException("An error occurred while creating the user: " + e.getMessage());
        }
    }

    private void createUserResources(Long id) {
        try {
            cartFeignClient.insert(id);
            wishlistFeignClient.insert(id);
        } catch (FeignClientException e) {
            throw new ObjectNotCreatedException("An error occurred while creating the user resources.");
        }
    }

    private User fromDTO(UserDTO objUser) {
        return new User(
                objUser.getName(),
                objUser.getEmail(),
                objUser.getPassword(),
                objUser.getAddress(),
                objUser.getPhone());
    }
}
