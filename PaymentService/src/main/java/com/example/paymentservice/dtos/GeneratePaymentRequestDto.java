package com.example.paymentservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class GeneratePaymentRequestDto {
    private String orderId;
}
