package com.example.payment_test.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortOnePaymentResponse {

    private String id;
    private String status;
    private Amount amount;
    private String currency;
    private String orderName;


    @Getter @NoArgsConstructor
    public static class Amount{
        private Long total;
        private int paid;
        private int cancelled;
    }
}
