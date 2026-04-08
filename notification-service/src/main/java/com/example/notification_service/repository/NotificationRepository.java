package com.example.notification_service.repository;

import com.example.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByUserId(UUID userId);


    @Query("""
        SELECT SUM(n.amount) FROM Notification n
        WHERE n.userId = :userId
        AND MONTH(n.createdAt) = MONTH(CURRENT_DATE)
        AND YEAR(n.createdAt) = YEAR(CURRENT_DATE)
        AND n.highSpending = false
    """)
    BigDecimal sumCurrentMonthByUserId(@Param("userId") UUID userId);
}