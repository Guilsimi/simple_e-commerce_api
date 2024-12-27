package com.example.ec_wishlist.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_wishlist.entities.ListProductAssociation;
import com.example.ec_wishlist.entities.Wishlist;
import com.example.ec_wishlist.entities.foreign.product.Product;
import com.example.ec_wishlist.feignclients.ProductFeignClient;
import com.example.ec_wishlist.repositories.ListProductRepository;
import com.example.ec_wishlist.services.exceptions.ObjectNotCreatedException;
import com.example.ec_wishlist.services.exceptions.ResourceNotFoundException;

import feign.FeignException;

@Service
public class AssociationServices {

    @Autowired
    private ListProductRepository wishlistProductRepository;

    @Autowired
    private WishlistServices listServices;

    @Autowired
    private ProductFeignClient productFeignClient;

    public void insertAssociation(ListProductAssociation association) {
        try {
            wishlistProductRepository.save(association);
        } catch (Exception e) {
            throw new ObjectNotCreatedException("Erro ao inserir o produto na lista de pedidos " + e.getMessage());
        }

    }

    public void removeAssociation(Long id, Long productId) {
        try {
            Wishlist wishlist = listServices.findById(id);
            List<ListProductAssociation> associations = wishlistProductRepository.findByListId(id);

            ListProductAssociation association = associations
                    .stream()
                    .filter(ass -> ass.getProductId().equals(productId))
                    .findFirst().orElseThrow(() -> new ResourceNotFoundException("Erro ao excluir produto da lista"));

            wishlist.removeProductId(productId);
            wishlistProductRepository.delete(association);
            listServices.update(wishlist);
        } catch (Exception e) {
            throw new ObjectNotCreatedException("Erro ao remover o produto da lista " + e.getMessage());
        }
    }

    public List<Product> findAllProducts(Long id) {
        try {
            List<ListProductAssociation> objList = wishlistProductRepository.findByListId(id);
            List<Product> productList = new ArrayList<>();
            for (ListProductAssociation ass : objList) {
                productList.add(productFeignClient.findById(ass.getId()).getBody());
            }
            return productList;
        } catch (FeignException e) {
            throw new ResourceNotFoundException(
                    "Não foi possível se comunicar com o serviço de produtos " + e.getMessage());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Não foi possível encontrar a lista de produtos");
        }
    }

}
