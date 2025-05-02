package org.example.service;

import org.example.dto.response.AccountDTO;
import org.example.dto.request.CreateAccountRequest;
import org.example.entity.BankAccount;
import org.example.entity.User;
import org.example.repository.BankAccountRepository;
import org.example.repository.UserRepository;
import org.example.utils.AccountFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public AccountDTO createAccount(String userEmail, CreateAccountRequest request) {
        try{
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            BankAccount account = AccountFactory.createAccount(request.getAccountType());
            account.setOwner(user);
            account.setAccountNumber(UUID.randomUUID().toString());
            account.setBalance(request.getInitialDeposit());
            bankAccountRepository.save(account);

            emailService.sendAccountCreationEmail(
                    user.getEmail(),
                    "Account Creation",
                    "Your account has been created successfully."
            );
            return mapToDTO(account);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }

    }

    public List<AccountDTO> getAccountsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return bankAccountRepository.findByOwner(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AccountDTO mapToDTO(BankAccount acc) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountType(acc.getAccountType());
        dto.setAccountNumber(acc.getAccountNumber());
        dto.setBalance(acc.getBalance());
        return dto;
    }
}
