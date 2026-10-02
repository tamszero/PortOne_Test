package com.example.payment_test.payment.service;

import com.example.payment_test.payment.client.PortOneClient;
import com.example.payment_test.payment.dto.PortOnePaymentResponse;
import com.example.payment_test.payment.entity.Payment;
import com.example.payment_test.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PortOneClient portOneClient;
    private final PaymentRepository paymentRepository;

    public PaymentService(
            PortOneClient portOneClient,
            PaymentRepository paymentRepository
    ) {
        this.portOneClient = portOneClient;
        this.paymentRepository = paymentRepository;
    }

    /**
     * 결제 검증
     */
    @Transactional
    public Payment verifyPayment(String paymentId) {

        // 1. 우리 DB에서 주문 조회
        Payment payment = paymentRepository
                .findByPaymentId(paymentId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "존재하지 않는 결제입니다."
                        )
                );

        System.out.println("========== DB 결제 정보 ==========");
        System.out.println("paymentId = " + payment.getPaymentId());
        System.out.println("orderName = " + payment.getOrderName());
        System.out.println("amount = " + payment.getAmount());
        System.out.println("status = " + payment.getStatus());
        System.out.println("================================");


        // 2. PortOne에서 실제 결제 정보 조회
        PortOnePaymentResponse portOnePayment =
                portOneClient.getPayment(paymentId);

        System.out.println("========== PORTONE 결제 정보 ==========");
        System.out.println("status = " + portOnePayment.getStatus());
        System.out.println(
                "amount = " +
                        portOnePayment.getAmount().getTotal()
        );
        System.out.println(
                "currency = " +
                        portOnePayment.getCurrency()
        );
        System.out.println("======================================");


        // 3. 결제 상태 확인
        if (!"PAID".equals(portOnePayment.getStatus())) {

            throw new IllegalStateException(
                    "결제가 완료되지 않았습니다."
            );
        }


        // 4. 금액 검증
        if (payment.getAmount()
                != portOnePayment.getAmount().getTotal()) {

            throw new IllegalStateException(
                    "결제 금액이 일치하지 않습니다."
            );
        }


        // 5. 이미 결제 완료된 주문인지 확인
        if (payment.getStatus()
                == com.example.payment_test.payment.entity.PaymentStatus.PAID) {

            throw new IllegalStateException(
                    "이미 처리된 결제입니다."
            );
        }


        // 6. 검증 성공 → PAID
        payment.paid();

        paymentRepository.save(payment);


        System.out.println("========== 결제 검증 성공 ==========");
        System.out.println("paymentId = " + payment.getPaymentId());
        System.out.println("status = " + payment.getStatus());
        System.out.println("==================================");


        return payment;
    }
    public Payment createPayment(
            String paymentId,
            String orderName,
            int amount
    ) {

        Payment payment = new Payment(
                paymentId,
                orderName,
                amount
        );

        return paymentRepository.save(payment);
    }
}