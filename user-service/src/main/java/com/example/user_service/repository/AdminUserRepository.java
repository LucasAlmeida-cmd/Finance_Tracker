package com.example.user_service.repository;

import com.example.user_service.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminUserRepository extends JpaRepository<AdminUser, UUID> {
}
