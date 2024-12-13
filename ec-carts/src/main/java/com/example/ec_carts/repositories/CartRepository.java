package com.example.ec_carts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ec_carts.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
