package com.example.ec_user.services.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.ec_user.entities.records.RegisterResponse;

@ControllerAdvice
public class ValidationExeption {

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<RegisterResponse> handle(MethodArgumentNotValidException e) {

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new RegisterResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), errors));

    }

}
