package com.example.ec_carts.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_carts.entities.Cart;
import com.example.ec_carts.services.CartServices;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/insert")
public class CreateCart {

    @Autowired
    private CartServices services;

    @PostMapping("/new/cart/{id}")
    public ResponseEntity<Void> insert(@PathVariable Long id) {
        Cart cart = new Cart(id, null);
        services.insert(cart);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
