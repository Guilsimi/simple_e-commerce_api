package com.example.ec_wishlist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ec_wishlist.entities.ListProductAssociation;

public interface ListProductRepository extends JpaRepository<ListProductAssociation, Long> {

    List<ListProductAssociation> findByListId(Long id);

}
