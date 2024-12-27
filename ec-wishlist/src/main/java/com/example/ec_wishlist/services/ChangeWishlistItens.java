package com.example.ec_wishlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_wishlist.entities.ListProductAssociation;
import com.example.ec_wishlist.entities.Wishlist;
import com.example.ec_wishlist.services.exceptions.ResourceNotUpdatedException;

@Service
public class ChangeWishlistItens {

    @Autowired
    private WishlistServices services;

    @Autowired
    private AssociationServices associationServices;

    public void addProductToWishlist(Long id, Long productId) {
        try {
            ListProductAssociation listProductAssociation = new ListProductAssociation(null, id, productId);
            Wishlist wishlist = services.findById(id);
            associationServices.insertAssociation(listProductAssociation);
            wishlist.addProductId(productId);
            services.update(wishlist);
        } catch (Exception e) {
            throw new ResourceNotUpdatedException("Erro ao adicionar o produto na lista de desejos");
        }
    }

    public void removeProductFromWishlist(Long id, Long productId) {
        try {
        associationServices.removeAssociation(id, productId);
    } catch (Exception e) {
        throw new ResourceNotUpdatedException("Erro ao remover o produto da lista");
    }
    }

}
