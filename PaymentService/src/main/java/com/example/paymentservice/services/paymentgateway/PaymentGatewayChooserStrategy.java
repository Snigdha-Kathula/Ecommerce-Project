package com.example.paymentservice.services.paymentgateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayChooserStrategy {
    private RazorpayGateway razorpayGateway;
    private StripePaymentGateway stripePaymentGateway;
    @Autowired
    public PaymentGatewayChooserStrategy(RazorpayGateway razorpayGateway, StripePaymentGateway stripePaymentGateway) {
        this.razorpayGateway = razorpayGateway;
        this.stripePaymentGateway = stripePaymentGateway;
    }
    public PaymentGateway getBestPaymentGateway(){
        return stripePaymentGateway;
    }
}
