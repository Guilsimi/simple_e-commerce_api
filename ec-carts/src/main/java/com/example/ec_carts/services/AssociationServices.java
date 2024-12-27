package com.example.ec_carts.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_carts.entities.Cart;
import com.example.ec_carts.entities.CartProductAssociation;
import com.example.ec_carts.entities.foreign.product.Product;
import com.example.ec_carts.feignclients.ProductFeignClient;
import com.example.ec_carts.repositories.CartProductRepository;
import com.example.ec_carts.services.exceptions.ObjectNotCreatedException;
import com.example.ec_carts.services.exceptions.ResourceNotFoundException;

import feign.FeignException;

@Service
public class AssociationServices {

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private CartServices cartServices;

    @Autowired
    private ProductFeignClient productFeignClient;

    public void insertAssociation(CartProductAssociation association) {
        try {
            cartProductRepository.save(association);
        } catch (Exception e) {
            throw new ObjectNotCreatedException("Erro ao inserir o produto no carrinho " + e.getMessage());
        }
    }

    public void removeAssociation(Long id, Long productId) {
        try {
            Cart cart = cartServices.findById(id);
            List<CartProductAssociation> associations = cartProductRepository.findByCartId(id);

            CartProductAssociation association = associations
                    .stream()
                    .filter(ass -> ass.getProductId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Erro ao excluir produto do carrinho"));

            cart.removeProductId(productId);
            cartProductRepository.delete(association);
            cartServices.update(cart);
        } catch (Exception e) {
            throw new ObjectNotCreatedException("Erro ao remover o produto do carrinho " + e.getMessage());
        }
    }

    public List<Product> findAllProducts(Long id) {
        try {
            List<CartProductAssociation> objList = cartProductRepository.findByCartId(id);
            List<Product> productList = new ArrayList<>();
            for (CartProductAssociation ass : objList) {
                productList.add(productFeignClient.findById(ass.getId()).getBody());
            }
            return productList;
        } catch (FeignException e) {
            throw new ResourceNotFoundException("Não foi possível se comunicar com o serviço de produtos " + e.getMessage());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Não foi possível encontrar a lista de produtos");
        }
    }

}
