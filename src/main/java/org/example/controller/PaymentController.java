package org.example.controller;

import org.example.dto.request.PaymentRequest;
import org.example.utils.PaymentGatewayFactory;
import org.example.utils.PaymentManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/pay")
    public ResponseEntity<String> makePayment(@RequestBody PaymentRequest request) {
        PaymentManager manager = PaymentManager.getInstance();

        manager.setGateway(PaymentGatewayFactory.getPaymentGateway(request.getProvider()));

        String response = manager.processPayment(request.getFromAccount(), request.getToMerchant(), request.getAmount());

        return ResponseEntity.ok(response);
    }
}
