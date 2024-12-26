package com.example.ec_product.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_product.entities.Product;
import com.example.ec_product.service.ProductService;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/products")
public class ProductResources {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product prodObj = productService.findById(id);
        
        return ResponseEntity.ok().body(prodObj);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> prodList = productService.findAll();
        return ResponseEntity.ok().body(prodList);
    }

    @PutMapping(value = "/decrease/{id}/{quantity}")
    public ResponseEntity<Void> updateQuantity(@PathVariable Long id, @PathVariable Integer quantity) {
        productService.updateQuantity(id, quantity);
        return ResponseEntity.ok().body(null);
    }

}
