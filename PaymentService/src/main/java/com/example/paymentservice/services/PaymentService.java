package com.example.paymentservice.services;

import com.example.paymentservice.services.paymentgateway.PaymentGatewayChooserStrategy;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private PaymentGatewayChooserStrategy paymentGatewayChooserStrategy;

    @Autowired
    public PaymentService(PaymentGatewayChooserStrategy paymentGatewayChooserStrategy) {
        this.paymentGatewayChooserStrategy = paymentGatewayChooserStrategy;
    }

    public String generatePaymentLink(String orderId) throws RazorpayException, StripeException {
        return paymentGatewayChooserStrategy.getBestPaymentGateway().generateLink(orderId);
    }

}
