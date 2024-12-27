package com.example.ec_wishlist.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_wishlist.entities.Wishlist;
import com.example.ec_wishlist.services.WishlistServices;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/insert")
public class CreateWishList {

    @Autowired
    private WishlistServices services;

    @PostMapping("/new/wishlist/{id}")
    public ResponseEntity<Void> insert(@PathVariable Long id) {
        Wishlist wishlist = new Wishlist(id, null);
        services.insert(wishlist);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
