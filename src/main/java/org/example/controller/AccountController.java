package org.example.controller;

import org.example.dto.response.AccountDTO;
import org.example.dto.request.CreateAccountRequest;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>>getAccounts(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(accountService.getAccountsByUser(userDetails.getUsername()));
    }
}
