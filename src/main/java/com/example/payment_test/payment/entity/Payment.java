package com.example.payment_test.payment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // PortOne paymentId
    @Column(nullable = false, unique = true)
    private String paymentId;

    // 주문명
    @Column(nullable = false)
    private String orderName;

    // 우리가 예상한 결제 금액
    @Column(nullable = false)
    private int amount;

    // 결제 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    public Payment(
            String paymentId,
            String orderName,
            int amount
    ) {
        this.paymentId = paymentId;
        this.orderName = orderName;
        this.amount = amount;
        this.status = PaymentStatus.READY;
    }

    public void paid() {
        this.status = PaymentStatus.PAID;
    }
}