package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.response.TransactionDTO;
import org.example.dto.request.TransactionRequest;
import org.example.dto.request.TransferRequest;
import org.example.entity.BankAccount;
import org.example.entity.Transaction;
import org.example.entity.User;
import org.example.exception.AccountNotFoundException;
import org.example.exception.InsufficientBalanceException;
import org.example.exception.UnauthorizedAccessException;
import org.example.repository.BankAccountRepository;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    public TransactionDTO deposit(TransactionRequest request) {
        BankAccount account = bankAccountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        account.setBalance(account.getBalance().add(request.getAmount()));
        bankAccountRepository.save(account);
        return logTransaction(account, request.getEmail(), request.getAmount(), "DEPOSIT", "SUCCESS");
    }

    public TransactionDTO withdraw(TransactionRequest request) {
        BankAccount account = bankAccountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("You don't have sufficient amount to withdrawal");
        }
        if (!account.getOwner().getEmail().equals(request.getEmail())) {
            throw new UnauthorizedAccessException("You are not allowed to access this account");
        }
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        bankAccountRepository.save(account);
        return logTransaction(account, request.getEmail(), request.getAmount(), "WITHDRAW", "SUCCESS");
    }

    public List<TransactionDTO> getTransactionHistory(String accountNumber) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        List<Transaction> transactions = transactionRepository.findByBankAccount(account);

        return transactions.stream().map(txn -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setAmount(txn.getAmount());
            dto.setType(txn.getType());
            dto.setTimestamp(txn.getTimestamp());
            dto.setStatus(txn.getStatus());
            return dto;
        }).toList();
    }

    @Async("asyncExecutor")
    public CompletableFuture<Void> asyncTransfer(String fromAccountNumber, String toAccountNumber, String email, double amount) {
        transferSync(fromAccountNumber, toAccountNumber, email, amount);
        return CompletableFuture.completedFuture(null);
    }

    @Transactional
    public void transferSync(String fromAccountNumber, String toAccountNumber, String email, double amount) {

        BankAccount fromAccount = bankAccountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Sender Account not found"));
        BankAccount toAccount = bankAccountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));
        BigDecimal transferAmount = BigDecimal.valueOf(amount);

        if (fromAccount.getBalance().compareTo(transferAmount) < 0) {
            throw new InsufficientBalanceException("you don't have sufficient amount for transfer");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
        toAccount.setBalance(toAccount.getBalance().add(transferAmount));

        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);
        logTransaction(fromAccount, email, transferAmount, "TRANSFER_OUT", "SUCCESS");
        logTransaction(toAccount, email, transferAmount, "TRANSFER_IN", "SUCCESS");

    }

    public void transfer(TransferRequest request) {
        asyncTransfer(request.getFromAccountNumber(), request.getToAccountNumber(), request.getEmail(), request.getAmount().doubleValue());
    }

    private TransactionDTO logTransaction(BankAccount account, String email, BigDecimal amount, String type, String status) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Transaction txn = new Transaction();
        txn.setBankAccount(account);
        txn.setUser(user);
        txn.setAmount(amount);
        txn.setType(type);
        txn.setStatus(status);

        transactionRepository.save(txn);

        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(amount);
        dto.setStatus(status);
        dto.setType(type);
        dto.setTimestamp(txn.getTimestamp());
        return dto;
    }
}
