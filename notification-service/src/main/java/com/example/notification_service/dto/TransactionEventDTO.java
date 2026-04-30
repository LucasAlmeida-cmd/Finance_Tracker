package com.example.notification_service.dto;

import com.example.notification_service.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEventDTO {
    private UUID transactionId;
    private UUID userId;
    private BigDecimal amount;
    private TransactionType type;
    private String category;
    private LocalDate date;
}