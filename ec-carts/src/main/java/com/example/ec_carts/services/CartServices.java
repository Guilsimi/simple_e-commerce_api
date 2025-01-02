package com.example.ec_carts.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_carts.entities.Cart;
import com.example.ec_carts.repositories.CartRepository;
import com.example.ec_carts.services.exceptions.ObjectNotCreatedException;
import com.example.ec_carts.services.exceptions.ResourceNotFoundException;
import com.example.ec_carts.services.exceptions.ResourceNotUpdatedException;

@Service
public class CartServices {

    @Autowired
    private CartRepository cartRepository;

    public Cart findById(Long id) {
        if (id == null) {
            throw new ResourceNotFoundException("Impossível localizar objeto: ID null");
        }
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado"));
    }

    public void insert(Cart cart) {
        try {
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new ObjectNotCreatedException("Erro ao gerar o carrinho " + e.getMessage());
        }
    }

    public void update(Cart cart) {
        try {
            Cart newCart = findById(cart.getId());
            updateData(newCart, cart);
            cartRepository.save(newCart);
        } catch (Exception e) {
            throw new ResourceNotUpdatedException("Erro ao atualizar o carrinho");
        }
    }

    private void updateData(Cart newCart, Cart objCart) {
        newCart.setId(
                objCart.getId() != null ? objCart.getId() : newCart.getId());
        newCart.setProductId(
                objCart.getProductId() != null ? objCart.getProductId() : newCart.getProductId());
    }

}
