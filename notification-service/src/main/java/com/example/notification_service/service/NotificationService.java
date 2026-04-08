package com.example.notification_service.service;


import com.example.notification_service.dto.TransactionEventDTO;
import com.example.notification_service.entity.Notification;

import com.example.notification_service.enums.TransactionType;
import com.example.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository repository;

    // limite mensal de gastos para disparar alerta (R$ 5000)
    private static final BigDecimal MONTHLY_LIMIT = new BigDecimal("5000.00");

    public void processTransaction(TransactionEventDTO event) {

        // só processa despesas, receitas não geram alerta
        if (event.getType() == TransactionType.INCOME) {
            log.info("Receita recebida, nenhum alerta necessário. userId={}", event.getUserId());
            return;
        }

        boolean highSpending = isHighSpending(event);

        String message = buildMessage(event, highSpending);

        Notification notification = Notification.builder()
                .userId(event.getUserId())
                .message(message)
                .amount(event.getAmount())
                .category(event.getCategory())
                .highSpending(highSpending)
                .build();

        repository.save(notification);

        log.info("Notificação salva para userId={} | highSpending={}",
                event.getUserId(), highSpending);
    }

    private boolean isHighSpending(TransactionEventDTO event) {
        BigDecimal totalMonth = repository.sumCurrentMonthByUserId(event.getUserId());

        // se ainda não tem gastos no mês, evita NullPointerException
        if (totalMonth == null) {
            totalMonth = BigDecimal.ZERO;
        }

        BigDecimal totalComNovoGasto = totalMonth.add(event.getAmount());
        return totalComNovoGasto.compareTo(MONTHLY_LIMIT) > 0;
    }

    private String buildMessage(TransactionEventDTO event, boolean highSpending) {
        if (highSpending) {
            return String.format(
                    "⚠️ Alerta: seus gastos mensais ultrapassaram R$ %.2f! " +
                            "Última despesa: R$ %.2f na categoria '%s'.",
                    MONTHLY_LIMIT, event.getAmount(), event.getCategory()
            );
        }

        return String.format(
                "Nova despesa registrada: R$ %.2f na categoria '%s'.",
                event.getAmount(), event.getCategory()
        );
    }
}