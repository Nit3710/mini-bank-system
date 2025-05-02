package org.example.utils;

public interface PaymentGateway {
    String pay(String from, String to, double amount);
}
