package com.example.notification_service.consumer;


import com.example.notification_service.dto.TransactionEventDTO;
import com.example.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "transaction.created",
            groupId = "notification-group"
    )
    public void consume(TransactionEventDTO event) {
        log.info("Evento recebido do Kafka: transactionId={} userId={}",
                event.getTransactionId(), event.getUserId());
        try {
            notificationService.processTransaction(event);
        } catch (Exception e) {
            log.error("Erro ao processar evento: {}", e.getMessage());
        }
    }
}
