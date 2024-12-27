package com.example.ec_carts.resources;

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

import com.example.ec_carts.entities.foreign.product.Product;
import com.example.ec_carts.services.AssociationServices;
import com.example.ec_carts.services.ChangeCartItens;
import com.example.ec_carts.services.Utils;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/cart")
public class CartResource {

    @Autowired
    private AssociationServices associationServices;

    @Autowired
    private ChangeCartItens cartItens;

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
        
        cartItens.addProductToCart(id, product);

        return new ResponseEntity<String>("Produto adicionado com sucesso", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/remove/{product}")
    public ResponseEntity<String> removeProduct(@RequestHeader("Authorization") String bearerToken,
            @PathVariable Long product) {
        Long id = utils.getUserId(bearerToken);
        
        cartItens.removeProductFromCart(id, product);

        return new ResponseEntity<String>("Produto removido com sucesso", HttpStatus.NO_CONTENT);
    }

}
