package com.example.payment_test.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentCreateRequest {

    private String orderName;

    private int amount;
}