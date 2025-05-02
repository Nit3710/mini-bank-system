package org.example.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {
    private BigDecimal amount;
    private String type;
    private String status;
    private LocalDateTime timestamp;
}
