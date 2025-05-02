package org.example.controller;

import org.example.dto.response.TransactionDTO;
import org.example.dto.request.TransactionRequest;
import org.example.dto.request.TransferRequest;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDTO> deposit(@RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.deposit(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(@RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.withdraw(request));
    }

    @PostMapping("/async-transfer")
    public ResponseEntity<String> asyncTransfer(@RequestBody TransferRequest request) {
        transactionService.asyncTransfer(request.getFromAccountNumber(),
                request.getToAccountNumber(),
                request.getEmail(),
                request.getAmount().doubleValue());
        return ResponseEntity.accepted().body("Transfer is processing in background.");
    }

    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getHistory(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getTransactionHistory(accountNumber));
    }
}
