package com.example.ec_payment.services;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.example.ec_payment.entites.Payment;
import com.example.ec_payment.repositories.PaymentRepository;
import com.openpix.sdk.ChargeBuilder;
import com.openpix.sdk.ChargeResponse;
import com.openpix.sdk.WooviSDK;

@RefreshScope
@Service
public class PaymentServices {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private WooviSDK woovi;

    public ChargeResponse insert(Payment payment) throws InterruptedException, ExecutionException {
        Integer paymentValue = (int) (payment.getPrice() * 100);
        repository.save(payment);
        ChargeBuilder chargeBuilder = new ChargeBuilder()
                .value(paymentValue)
                .comment("Pagamento SimpleEcommerce")
                .correlationID("pedido-" + payment.getId());

        return woovi.createChargeAsync(chargeBuilder).get();
    }

}
