package com.example.ec_order.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.ec_order.entites.Order;
import com.example.ec_order.entites.Product;
import com.example.ec_order.entites.enums.Status;

@Service
public class GetOrderAndProducts {

    @Value("${hr-worker.host}")
    private String host;

    @Autowired
    private RestTemplate restTemplate;

    public Order getOrder(long productId, int quantity) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", ""+productId);

        Product product = restTemplate.getForObject(host + "/products/{id}", Product.class, uriVariables);
        return new Order(null, product.getName(),new Date(), (product.getPrice()*quantity), Status.CONCLUÍDO);
    }
}
