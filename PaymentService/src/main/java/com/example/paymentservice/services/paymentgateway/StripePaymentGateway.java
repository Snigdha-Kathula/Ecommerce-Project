package com.example.paymentservice.services.paymentgateway;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGateway implements PaymentGateway{
    @Override
    public String generateLink(String orderId) throws StripeException {

        Stripe.apiKey = "sk_test_51OqF7nSCLwYXe5gDueURcXfhVJWtrlkeSzo9oTLplOD5mkqBSoNp8LM5hDINIAVStj6jqGyYFoqmWLzs4SFjCXF0003TvTFK0B";
        ProductCreateParams productParams =
                ProductCreateParams.builder().setName("Gold Plan").build();
        Product product = Product.create(productParams);

        PriceCreateParams priceParms =
                PriceCreateParams.builder()
                        .setCurrency("inr")
                        .setUnitAmount(1000L)
//                        .setRecurring(
//                                PriceCreateParams.Recurring.builder()
//                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
//                                        .build()
//                        )
                        .setProduct(product.getId())
                        .build();
        Price price = Price.create(priceParms);

        PaymentLinkCreateParams params =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .setRedirect(
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("https://www.google.com")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();
        PaymentLink paymentLink = PaymentLink.create(params);
        return paymentLink.getUrl();

    }
}
//sk_test_51OqF7nSCLwYXe5gDueURcXfhVJWtrlkeSzo9oTLplOD5mkqBSoNp8LM5hDINIAVStj6jqGyYFoqmWLzs4SFjCXF0003TvTFK0B