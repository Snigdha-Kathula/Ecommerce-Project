package com.example.paymentservice.controllers;

import com.example.paymentservice.dtos.GeneratePaymentRequestDto;
import com.example.paymentservice.services.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping
    public String generatePaymentLink(@RequestBody GeneratePaymentRequestDto requestDto){
        try {
            return paymentService.generatePaymentLink(requestDto.getOrderId());
        }catch (RazorpayException | StripeException e ){
            return e.getMessage();
        }
    }
}
