package com.example.transaction_service.dtos;

import com.example.transaction_service.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequestDTO {

    @NotBlank(message = "Descrição obrigatória")
    private String description;

    @NotNull(message = "Valor obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal amount;

    @NotNull(message = "Tipo obrigatório")
    private TransactionType type;

    @NotBlank(message = "Categoria obrigatória")
    private String category;

    @NotNull(message = "Data obrigatória")
    private LocalDate date;
}