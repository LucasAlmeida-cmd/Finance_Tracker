package com.example.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_user")
@Setter
@Getter
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
public abstract class User {

    @Id
    private UUID id;

    @Column(name = "nome_user", nullable = false, length = 100)
    private String nome;

    @Column(name = "email_user", nullable = false, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    private Roles role;

    public User() {
    }



}
