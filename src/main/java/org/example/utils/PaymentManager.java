package org.example.utils;

public class PaymentManager {

    private static PaymentManager instance;

    private PaymentGateway gateway;

    private PaymentManager() {

    }

    public static synchronized PaymentManager getInstance() {
        if (instance == null) {
            instance = new PaymentManager();
        }
        return instance;
    }

    public void setGateway(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public String processPayment(String from, String to, double amount) {
        if (gateway == null) {
            throw new IllegalStateException("Payment Gateway not initialized.");
        }
        return gateway.pay(from, to, amount);
    }
}
