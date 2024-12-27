package com.example.ec_payment.services;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.example.ec_payment.entites.Payment;
// import com.example.ec_payment.repositories.ChargesRepository;
import com.example.ec_payment.repositories.PaymentRepository;
import com.example.ec_payment.services.exceptions.ChargeNotFoundException;
import com.example.ec_payment.services.exceptions.ObjectNotCreatedException;
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

    public ChargeResponse insert(Payment payment) throws ExecutionException, InterruptedException {
        try {
            Integer paymentValue = (int) (payment.getPrice() * 100);

            repository.save(payment);
            ChargeBuilder chargeBuilder = new ChargeBuilder()
                    .value(paymentValue)
                    .comment("Pagamento SimpleEcommerce")
                    .correlationID("pedido-" + payment.getId());

            ChargeResponse response = woovi.createChargeAsync(chargeBuilder).get();

            return response;
        } catch (InterruptedException e) {
            throw new InterruptedException("O sistema foi interrompido enquanto gerava o pagamento " + e.getMessage());
        } catch (ChargeNotFoundException e) {
            throw new ExecutionException("Houve um erro na execução do sistema " + e.getMessage(), new Throwable());
        } catch (Exception e) {
            throw new ObjectNotCreatedException("Erro ao criar pagamento");
        }

    }

    public ChargeResponse findById(Long id) {
        try {
            ChargeResponse charge = woovi.getChargeAsync("pedido-" + id).get();

            return charge;
        } catch (Exception e) {
            throw new ChargeNotFoundException("Cobrança não encontrada");
        }
    }

}
