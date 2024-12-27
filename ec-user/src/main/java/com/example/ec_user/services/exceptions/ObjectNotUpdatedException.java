package com.example.ec_user.services.exceptions;

public class ObjectNotUpdatedException extends RuntimeException {

    public ObjectNotUpdatedException(String msg) {
        super(msg);
    }

}