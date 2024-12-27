package com.example.ec_payment.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String error) {
        super(error);
    }

}
