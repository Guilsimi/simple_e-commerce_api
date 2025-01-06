package com.example.ec_payment.resources;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ec_payment.entites.Payment;
import com.example.ec_payment.entites.enums.foreign.order.Status;
import com.example.ec_payment.entites.foreign.order.Order;
import com.example.ec_payment.feignclients.OrderFeignClient;
import com.example.ec_payment.services.PaymentServices;
import com.example.ec_payment.services.Utils;
import com.openpix.sdk.ChargeResponse;

import jakarta.annotation.Resource;


@Resource
@RestController
@RequestMapping(value = "/payment")
public class PaymentResource {

    @Autowired
    private PaymentServices paymentServices;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private Utils utils;

    @PostMapping(value = "/generate/{id}/{paymethod}")
    public ResponseEntity<ChargeResponse> insert(
            @PathVariable Long id,
            @PathVariable Integer paymethod,
            @RequestHeader("Authorization") String bearerToken) throws InterruptedException, ExecutionException {

        Order order = orderFeignClient.findById(id).getBody();
        String email = utils.getUserEmail(bearerToken);

        Payment payment = new Payment(id,
                new Date(),
                order.getTotal(),
                email,
                Status.PENDENTE.getCode(),
                paymethod);

        ChargeResponse response = paymentServices.insert(payment);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChargeResponse> findChargeById(@PathVariable Long id) {
        ChargeResponse response = paymentServices.findById(id);
        System.out.println("Status = " + response.getCharge().getStatus());
        updateOrder(id);
        return ResponseEntity.ok().body(response);
    }

    private void updateOrder(Long id) {
        ChargeResponse response = paymentServices.findById(id);
        if (response.getCharge().getStatus().equals("EXPIRED")) {
            orderFeignClient.update(id, 6);
        } else if (response.getCharge().getStatus().equals("COMPLETED")) {
            orderFeignClient.update(id, 2);
        } 
    }
    

}
