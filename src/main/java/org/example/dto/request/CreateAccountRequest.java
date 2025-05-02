package org.example.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class CreateAccountRequest {
    private String accountType;
    private BigDecimal initialDeposit;
}
