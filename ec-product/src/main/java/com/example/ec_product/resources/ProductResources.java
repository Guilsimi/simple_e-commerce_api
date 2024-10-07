package com.example.ec_product.resources;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_product.entities.Product;
import com.example.ec_product.service.ProductService;

import jakarta.annotation.Resource;

@Resource
@RestController
@RequestMapping(value = "/products")
public class ProductResources {

    private static Logger logger = LoggerFactory.getLogger(ProductResources.class);

    @Autowired
    Environment env;

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Product>> findById(@PathVariable Long id) {

        logger.info("Port = " + env.getProperty("local.server.port"));

        Optional<Product> prodObj = productService.findById(id);
        return ResponseEntity.ok().body(prodObj);
    }
}
