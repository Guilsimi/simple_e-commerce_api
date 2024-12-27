package com.example.ec_wishlist.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_wishlist.entities.foreign.product.Product;
import com.example.ec_wishlist.services.AssociationServices;
import com.example.ec_wishlist.services.ChangeWishlistItens;
import com.example.ec_wishlist.services.Utils;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/wishlist")
public class WishlistResource {
    @Autowired
    private ChangeWishlistItens wishlistItens;

    @Autowired
    private AssociationServices associationServices;

    @Autowired
    private Utils utils;

    @GetMapping
    public ResponseEntity<List<Product>> findById(@RequestHeader("Authorization") String bearerToken) {
        Long id = utils.getUserId(bearerToken);
        List<Product> products = associationServices.findAllProducts(id);

        return ResponseEntity.ok().body(products);
    }

    @PutMapping(value = "/add/{product}")
    public ResponseEntity<String> addProduct(@RequestHeader("Authorization") String bearerToken,
            @PathVariable Long product) {
        Long id = utils.getUserId(bearerToken);
        
        wishlistItens.addProductToWishlist(id, product);

        return new ResponseEntity<String>("Produto adicionado com sucesso", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/remove/{product}")
    public ResponseEntity<String> removeProduct(@RequestHeader("Authorization") String bearerToken,
            @PathVariable Long product) {
        Long id = utils.getUserId(bearerToken);
        
        wishlistItens.removeProductFromWishlist(id, product);

        return new ResponseEntity<String>("Produto removido com sucesso", HttpStatus.NO_CONTENT);
    }

}
