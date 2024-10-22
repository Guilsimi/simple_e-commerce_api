package com.example.ec_user.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_user.entities.User;
import com.example.ec_user.services.UserServices;

import jakarta.annotation.Resource;

@RestController
@Resource
@RequestMapping(value = "/users")
public class UserResources {

    @Autowired
    private UserServices userServices;

    @GetMapping(value = "/{id}")
    private ResponseEntity<User> findById(@PathVariable Long id) {
        User user = userServices.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/search")
    private ResponseEntity<User> findByEmail(@RequestParam String email) {
        User user = userServices.findByEmail(email);
        return ResponseEntity.ok().body(user);
    }

}