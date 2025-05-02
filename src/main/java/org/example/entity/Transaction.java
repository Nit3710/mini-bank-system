package org.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transaction")
@DiscriminatorColumn(name = "transaction_type")
@Data
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private BigDecimal amount;
    private String type;
    private LocalDateTime timestamp= LocalDateTime.now();
    private String status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private BankAccount bankAccount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
