package com.example.ec_carts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_carts.entities.Cart;
import com.example.ec_carts.entities.CartProductAssociation;
import com.example.ec_carts.entities.foreign.product.Product;
import com.example.ec_carts.feignclients.ProductFeignClient;
import com.example.ec_carts.repositories.CartProductRepository;
import com.example.ec_carts.repositories.CartRepository;
import com.example.ec_carts.services.exceptions.ResourceNotFoundException;

@Service
public class CartServices {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private ProductFeignClient productFeignClient;

    public Cart findById(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado"));
    }

    public void insert(Cart cart) {
        cartRepository.save(cart);
    }

    public void insertAssociation(CartProductAssociation association) {
        cartProductRepository.save(association);
    }

    public void insertProductId(Long id) {
        List<CartProductAssociation> associations = cartProductRepository.findByCartId(id);
        Cart cart = findById(id);
        associations.forEach(ass -> cart.addProductId(ass.getProductId()));
        update(cart);
    }

    public List<Product> findAllProducts(Long id) {
        List<CartProductAssociation> objList = cartProductRepository.findByCartId(id);
        List<Product> productList = new ArrayList<>();
        for (CartProductAssociation ass : objList) {
            productList.add(productFeignClient.findById(ass.getId()).getBody());
        }
        return productList;
    }

    public void update(Cart cart) {
        Cart newCart = findById(cart.getId());
        updateData(newCart, cart);
        cartRepository.save(newCart);
    }

    private void updateData(Cart newCart, Cart objCart) {
        newCart.setId(
                objCart.getId() != null ? objCart.getId() : newCart.getId());
        newCart.setProductId(
                objCart.getProductId() != null ? objCart.getProductId() : newCart.getProductId());
    }

}
