package org.example.utils;

import org.example.service.RazorPayService;
import org.example.service.StripeService;

public class PaymentGatewayFactory {

    public static PaymentGateway getPaymentGateway(String type) {
        return switch (type.toUpperCase()) {
            case "RAZORPAY" -> new RazorPayService();
            case "STRIPE" -> new StripeService();
            default -> throw new IllegalArgumentException("Unsupported Payment Gateway: " + type);
        };
    }
}
