package org.example.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class AccountDTO {
    private String accountType;
    private String accountNumber;
    private BigDecimal balance;
}
