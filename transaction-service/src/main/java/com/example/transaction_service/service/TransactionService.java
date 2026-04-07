package com.example.transaction_service.service;

import com.example.transaction_service.dtos.TransactionEventDTO;
import com.example.transaction_service.dtos.TransactionRequestDTO;
import com.example.transaction_service.dtos.TransactionResponseDTO;
import com.example.transaction_service.repository.TransactionRepository;
import com.example.transaction_service.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;
    private final KafkaTemplate<String, TransactionEventDTO> kafkaTemplate;

    private static final String TOPIC = "transaction.created";

    public TransactionResponseDTO create(TransactionRequestDTO dto, UUID userId) {
        Transaction transaction = Transaction.builder()
                .userId(userId)
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .type(dto.getType())
                .category(dto.getCategory())
                .date(dto.getDate())
                .build();

        Transaction saved = repository.save(transaction);

        // publica evento no Kafka após salvar
        publishEvent(saved);

        return toResponseDTO(saved);
    }

    public List<TransactionResponseDTO> findAll(UUID userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TransactionResponseDTO findById(UUID id, UUID userId) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transação não encontrada"));


        if (!transaction.getUserId().equals(userId)) {
            throw new TransactionNotFoundException("Transação não encontrada");
        }

        return toResponseDTO(transaction);
    }

    public TransactionResponseDTO update(UUID id, TransactionRequestDTO dto, UUID userId) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transação não encontrada"));

        if (!transaction.getUserId().equals(userId)) {
            throw new TransactionNotFoundException("Transação não encontrada");
        }

        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setCategory(dto.getCategory());
        transaction.setDate(dto.getDate());

        return toResponseDTO(repository.save(transaction));
    }

    public void delete(UUID id, UUID userId) {
        Transaction transaction = repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transação não encontrada"));

        if (!transaction.getUserId().equals(userId)) {
            throw new TransactionNotFoundException("Transação não encontrada");
        }

        repository.delete(transaction);
    }

    // publica evento no tópico Kafka
    private void publishEvent(Transaction transaction) {
        TransactionEventDTO event = TransactionEventDTO.builder()
                .transactionId(transaction.getId())
                .userId(transaction.getUserId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .category(transaction.getCategory())
                .date(transaction.getDate())
                .build();

        kafkaTemplate.send(TOPIC, event);
    }

    private TransactionResponseDTO toResponseDTO(Transaction t) {
        return TransactionResponseDTO.builder()
                .id(t.getId())
                .description(t.getDescription())
                .amount(t.getAmount())
                .type(t.getType())
                .category(t.getCategory())
                .date(t.getDate())
                .createdAt(t.getCreatedAt())
                .build();
    }
}