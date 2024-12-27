package com.example.ec_wishlist.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_wishlist.entities.Wishlist;
import com.example.ec_wishlist.repositories.ListRepository;
import com.example.ec_wishlist.services.exceptions.ObjectNotCreatedException;
import com.example.ec_wishlist.services.exceptions.ResourceNotFoundException;
import com.example.ec_wishlist.services.exceptions.ResourceNotUpdatedException;

@Service
public class WishlistServices {

    @Autowired
    private ListRepository wishlistRepository;

    public Wishlist findById(Long id) {
        Optional<Wishlist> wishlist = wishlistRepository.findById(id);
        return wishlist.orElseThrow(() -> new ResourceNotFoundException("Lista de pedidos não encontrada"));
    }

    public void insert(Wishlist wishlist) {
        try {
            wishlistRepository.save(wishlist);
        } catch (Exception e) {
            throw new ObjectNotCreatedException("Erro ao gerar a lista de pedidos " + e.getMessage());
        }
    }

    public void update(Wishlist wishlist) {
        try {
            Wishlist newWishlist = findById(wishlist.getId());
            updateData(newWishlist, wishlist);
            wishlistRepository.save(newWishlist);
        } catch (Exception e) {
            throw new ResourceNotUpdatedException("Erro ao atualizar a lista");
        }
    }

    private void updateData(Wishlist newWishlist, Wishlist objWishlist) {
        newWishlist.setId(
                objWishlist.getId() != null ? objWishlist.getId() : newWishlist.getId());
        newWishlist.setProductId(
                objWishlist.getProductId() != null ? objWishlist.getProductId() : newWishlist.getProductId());
    }

}
