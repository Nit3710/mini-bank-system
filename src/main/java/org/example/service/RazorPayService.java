package org.example.service;

import org.example.utils.PaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class RazorPayService implements PaymentGateway {

    @Override
    public String pay(String fromAccount, String toMerchant, double amount) {
        System.out.println("[RazorPayService] Initiating payment of ₹" + amount + " from " + fromAccount + " to " + toMerchant);
        return "Payment of ₹" + amount + " successful via RazorPay";
    }
}
