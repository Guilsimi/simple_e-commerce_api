package com.example.ec_carts.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ec_carts.entities.CartProductAssociation;

public interface CartProductRepository extends JpaRepository<CartProductAssociation, Long> {

    List<CartProductAssociation> findByCartId(Long id);

}
