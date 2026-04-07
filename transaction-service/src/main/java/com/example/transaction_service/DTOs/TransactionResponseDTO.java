package com.example.transaction_service.DTOs;

import com.example.transaction_service.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TransactionResponseDTO {
    private UUID id;
    private String description;
    private BigDecimal amount;
    private TransactionType type;
    private String category;
    private LocalDate date;
    private LocalDateTime createdAt;
}