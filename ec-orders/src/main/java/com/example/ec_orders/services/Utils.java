package com.example.ec_orders.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_orders.feignclients.OAuthFeignClient;
import com.example.ec_orders.feignclients.ProductsFeignClient;

@Service
public class Utils {

    @Autowired
    private ProductsFeignClient productsFeignClient;

    @Autowired
    private OAuthFeignClient authFeignClient;

    public Long getUserId(String token) {
        Long userId = authFeignClient.getLoggedUser(token).getBody().getId();
        return userId;
    }

    public Double total(List<Long> productsId, List<Integer> quantity) {
        Double total = 0.0;
        for (int i = 0; i < productsId.size(); i++) {
            Long productId = productsId.get(i);
            Integer qty = quantity.get(i);
            Double priceProduct = productsFeignClient.findById(productId).getBody().getPrice();

            total += priceProduct * qty;
        }
        return total;
    }

    public void decrease(List<Long> ids, List<Integer> quantity) {
        for (int i = 0; i < ids.size(); i++) {
            productsFeignClient.updateQuantity(ids.get(i), quantity.get(i));
        }
    }

}
