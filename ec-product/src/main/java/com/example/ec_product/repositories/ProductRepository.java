package com.example.ec_product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ec_product.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    

}