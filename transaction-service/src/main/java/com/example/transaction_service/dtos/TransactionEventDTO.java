package com.example.transaction_service.dtos;

import com.example.transaction_service.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class TransactionEventDTO {
    private UUID transactionId;
    private UUID userId;
    private BigDecimal amount;
    private TransactionType type;
    private String category;
    private LocalDate date;
}