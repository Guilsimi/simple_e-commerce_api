package com.example.ec_carts.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ec_carts.entities.Cart;
import com.example.ec_carts.repositories.CartRepository;
import com.example.ec_carts.services.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class CartServicesTest {

    @InjectMocks
    CartServices services;

    @Mock
    CartRepository repository;

    Cart cart;

    @BeforeEach
    public void setup() {
        cart = new Cart(1L, null);
    }

    @Test
    void methodFindByIdMustReturnTheRightCartSuccessfully() {
        when(repository.findById(cart.getId())).thenReturn(Optional.of(cart));
        Cart object = services.findById(cart.getId());
        assertEquals(Optional.of(cart), Optional.of(object));
        verify(repository).findById(cart.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void methodFindByIdMustReturnAnExceptionWhenIsAnNullCartId() {
        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            services.findById(null);
        });

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Impossível localizar objeto: ID null"));
        assertThat(e.getCause(), nullValue());

        verifyNoInteractions(repository);
    }

    @Test
    void methodFindByIdMustReturnAnExceptionWhenIsAnInvalidCartId() {
        final ResourceNotFoundException e = assertThrows(ResourceNotFoundException.class, () -> {
            services.findById(2L);
        });

        assertThat(e, notNullValue());
        assertThat(e.getMessage(), is("Carrinho não encontrado"));

        verify(repository).findById(2L);
        verifyNoMoreInteractions(repository);
    }

}