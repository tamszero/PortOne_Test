package com.example.payment_test.payment.client;

import com.example.payment_test.payment.dto.PortOnePaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * 포트원과의 통신 담당
 */
@Component
public class PortOneClient {

    private final RestClient restClient;
    private final String apiSecret;

    public PortOneClient(@Value("${portone.api-secret}") String apiSecret){
        this.apiSecret = apiSecret;

        this.restClient = RestClient.builder()
                .baseUrl("https://api.portone.io")
                .build();
    }

    public PortOnePaymentResponse getPayment(String paymentId){

        return restClient.get()
                .uri("/payments/{paymentId}", paymentId)
                .header(
                        "Authorization",
                        "PortOne " + apiSecret
                )
                .retrieve()
                .body(PortOnePaymentResponse.class);
    }
}
