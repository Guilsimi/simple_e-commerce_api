package com.example.ec_order.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ec_order.entites.Order;
import com.example.ec_order.entites.Product;
import com.example.ec_order.entites.enums.Status;
import com.example.ec_order.feignclient.ProductFeignClient;

@Service
public class GetOrderAndProducts {

    @Autowired
    private ProductFeignClient productFeignClient;

    public Order getOrder(long productId, int quantity) {

        Product product = productFeignClient.findById(productId).getBody();
        return new Order(null, product.getName(),new Date(), (product.getPrice()*quantity), Status.CONCLUÍDO);
    }
}
