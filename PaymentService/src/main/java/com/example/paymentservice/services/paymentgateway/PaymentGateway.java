package com.example.paymentservice.services.paymentgateway;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

@Service
public interface PaymentGateway {
    String generateLink(String orderId) throws RazorpayException, StripeException;
}
