package com.example.ec_wishlist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ec_wishlist.entities.Wishlist;

public interface ListRepository extends JpaRepository<Wishlist, Long> {

}
