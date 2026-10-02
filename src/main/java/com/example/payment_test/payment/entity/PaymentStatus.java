package com.example.payment_test.payment.entity;

public enum PaymentStatus {

    //결제 준비
    READY,

    //결제 완료
    PAID,

    //결제 실패
    FAILED,

    //결제 취소
    CANCELLED
}
