package com.example.user_service.repository;

import com.example.user_service.model.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientUserRepository extends JpaRepository<ClientUser,UUID > {

    Optional<ClientUser> findByCpf(String cpf);
}
