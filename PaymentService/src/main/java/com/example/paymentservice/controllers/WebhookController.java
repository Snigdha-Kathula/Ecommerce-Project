package com.example.paymentservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks")
public class WebhookController {
    @PostMapping
    public void stripeWebhook(){
        System.out.println("I hitted stripeWebhook");
    }
}
