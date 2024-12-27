package com.example.ec_orders.services.exceptions;

public class ResourceNotUpdatedException extends RuntimeException {

    public ResourceNotUpdatedException(String msg) {
        super(msg);
    }
}