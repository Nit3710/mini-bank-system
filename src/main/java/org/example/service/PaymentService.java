package org.example.service;

import org.example.utils.PaymentGateway;
import org.example.utils.PaymentGatewayFactory;
import org.example.utils.PaymentManager;

public class PaymentService {

    public String doPayment(String from, String to, double amount, String gatewayType) {
        PaymentGateway gateway = PaymentGatewayFactory.getPaymentGateway(gatewayType);
        PaymentManager manager = PaymentManager.getInstance();
        manager.setGateway(gateway);
        return manager.processPayment(from, to, amount);
    }
}
