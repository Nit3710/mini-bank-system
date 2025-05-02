package org.example.repository;

import org.example.entity.BankAccount;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount ,Long> {
    List<BankAccount> findByOwner(User user);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
