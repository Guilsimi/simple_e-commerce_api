package com.example.ec_payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ec_payment.entites.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
