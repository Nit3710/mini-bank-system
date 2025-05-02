package org.example.dto.request;

import lombok.Data;
@Data
public class PaymentRequest {
    private String provider;
    private String fromAccount;
    private String toMerchant;
    private double amount;
}
