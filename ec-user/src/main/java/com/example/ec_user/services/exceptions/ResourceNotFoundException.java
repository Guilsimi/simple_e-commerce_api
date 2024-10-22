package com.example.ec_user.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}