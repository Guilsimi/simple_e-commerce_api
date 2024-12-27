package com.example.ec_payment.services.exceptions;

public class ChargeNotFoundException extends RuntimeException {

    public ChargeNotFoundException(String error) {
        super(error);
    }

}
