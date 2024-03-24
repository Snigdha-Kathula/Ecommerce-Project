package com.example.paymentservice.services.paymentgateway;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import com.razorpay.RazorpayClient;
import org.springframework.stereotype.Service;

@Service
public class RazorpayGateway implements PaymentGateway{
    @Override
    public String generateLink(String orderId) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient("rzp_test_ZrEkWeeXGnyaam", "YqLYW0CYXvuoch0MsTFsBKAj");
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
//        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",1712123819);
        paymentLinkRequest.put("reference_id",orderId);
        paymentLinkRequest.put("description","Payment for policy no #23456");
        JSONObject customer = new JSONObject();
        customer.put("name","+919000090000");
        customer.put("contact","Gaurav Kumar");
        customer.put("email","gaurav.kumar@example.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",false);
        notify.put("email",false);
        paymentLinkRequest.put("notify",notify);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name","Jeevan Bima");
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://www.google.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
        return payment.toString();
    }
}
