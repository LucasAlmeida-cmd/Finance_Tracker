package com.example.user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_adminuser")
@Getter
@Setter
@DiscriminatorValue("ADMIN")
@PrimaryKeyJoinColumn(name = "id_admin", referencedColumnName = "id")
public class AdminUser extends User{
    @Column(name = "identi_admin", length = 100, unique = true)
    private UUID indentificacao;
}
