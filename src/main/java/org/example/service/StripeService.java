package org.example.service;

import org.example.utils.PaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class StripeService implements PaymentGateway {

    @Override
    public String pay(String fromAccount, String toMerchant, double amount) {
        System.out.println("[StripeService] Initiating payment of ₹" + amount + " from " + fromAccount + " to " + toMerchant);
        return "Payment of ₹" + amount + " successful via Stripe";
    }
}
