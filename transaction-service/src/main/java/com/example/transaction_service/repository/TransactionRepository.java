package com.example.transaction_service.repository;

import com.example.transaction_service.entity.Transaction;
import com.example.transaction_service.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUserId(UUID userId);
    List<Transaction> findByUserIdAndType(UUID userId, TransactionType type);
    List<Transaction> findByUserIdAndDateBetween(UUID userId, LocalDate start, LocalDate end);
}
