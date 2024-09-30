package com.example.ec_cart.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ec_cart.entites.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
