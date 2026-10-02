package com.example.payment_test.payment.controller;

import com.example.payment_test.payment.dto.PaymentCreateRequest;
import com.example.payment_test.payment.entity.Payment;
import com.example.payment_test.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    /**
     * 결제 주문 생성
     */
    @PostMapping
    public Payment createPayment(
            @RequestBody PaymentCreateRequest request
    ) {

        String paymentId =
                "pay-" +
                        UUID.randomUUID()
                                .toString()
                                .replace("-", "");

        return paymentService.createPayment(
                paymentId,
                request.getOrderName(),
                request.getAmount()
        );
    }


    /**
     * 결제 검증
     */
    @PostMapping("/verify")
    public Payment verifyPayment(
            @RequestBody Map<String, String> request
    ) {

        String paymentId =
                request.get("paymentId");

        return paymentService.verifyPayment(
                paymentId
        );
    }
}