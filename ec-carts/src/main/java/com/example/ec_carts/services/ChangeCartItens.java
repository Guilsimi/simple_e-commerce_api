package com.example.ec_carts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_carts.entities.Cart;
import com.example.ec_carts.entities.CartProductAssociation;
import com.example.ec_carts.services.exceptions.ResourceNotUpdatedException;

@Service
public class ChangeCartItens {

    @Autowired
    private CartServices services;

    @Autowired
    private AssociationServices associationServices;

    public void addProductToCart(Long id, Long productId) {
        try {
            CartProductAssociation cartProductAssociation = new CartProductAssociation(null, id, productId);
            Cart cart = services.findById(id);
            associationServices.insertAssociation(cartProductAssociation);
            cart.addProductId(productId);
            services.update(cart);
        } catch (Exception e) {
            throw new ResourceNotUpdatedException("Erro ao adicionar o produto no carrinho");
        }
    }

    public void removeProductFromCart(Long id, Long productId) {
        try {
            associationServices.removeAssociation(id, productId);
        } catch (Exception e) {
            throw new ResourceNotUpdatedException("Erro ao remover o produto do carrinho");
        }
    }

}
