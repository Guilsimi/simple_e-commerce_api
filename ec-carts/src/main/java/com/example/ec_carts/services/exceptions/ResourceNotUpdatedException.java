package com.example.ec_carts.services.exceptions;

public class ResourceNotUpdatedException extends RuntimeException {

    public ResourceNotUpdatedException(String msg) {
        super(msg);
    }
}