package com.example.ec_carts.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_carts.entities.Cart;
import com.example.ec_carts.entities.CartProductAssociation;
import com.example.ec_carts.repositories.CartProductRepository;

@Service
public class ChangeCartItens {

    @Autowired
    private CartServices services;

    @Autowired
    private CartProductRepository cartProductRepository;

    public void addProductToCart(Long id, Long productId) {
        CartProductAssociation cartProductAssociation = new CartProductAssociation(null, id, productId);
        Cart cart = services.findById(id);
        services.insertAssociation(cartProductAssociation);
        cart.addProductId(productId);
        services.update(cart);
    }

    public void removeProductFromCart(Long id, Long productId) {
        List<CartProductAssociation> associations = cartProductRepository.findByCartId(id);
        Cart cart = services.findById(id);
        for(CartProductAssociation ass : associations) {
            if (ass.getProductId().equals(productId)) {
                cartProductRepository.delete(ass);
            }
        }
        cart.removeProductId(productId);
        services.update(cart);
    }

}
