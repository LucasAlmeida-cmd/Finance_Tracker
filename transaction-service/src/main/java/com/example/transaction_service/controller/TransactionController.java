package com.example.transaction_service.controller;

import com.example.transaction_service.dtos.TransactionRequestDTO;
import com.example.transaction_service.dtos.TransactionResponseDTO;
import com.example.transaction_service.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    private UUID getCurrentUserId() {
        return UUID.fromString(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(
            @RequestBody @Valid TransactionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(dto, getCurrentUserId()));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll(getCurrentUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id, getCurrentUserId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid TransactionRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto, getCurrentUserId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id, getCurrentUserId());
        return ResponseEntity.noContent().build();
    }
}