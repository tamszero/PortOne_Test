package com.example.payment_test.payment.repository;

import com.example.payment_test.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

    Optional<Payment> findByPaymentId(String paymentId);
}
