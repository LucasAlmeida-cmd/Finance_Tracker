package com.example.user_service.model;

import com.example.user_service.exceptions.CpfInvalidException;
import com.example.user_service.exceptions.CpfNotNullException;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table (name = "tb_clientuser")
@Getter
@Setter
@DiscriminatorValue("CLIENTE")
@PrimaryKeyJoinColumn(name = "id_cliente", referencedColumnName = "id")
public class ClientUser extends User {

    @Column(name = "cpf_user", nullable = false, length = 100, unique = true)
    @CPF
    private String cpf;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "data_aniversario_usuario", nullable = false)
    private LocalDate dataAniversario;


    public ClientUser(UUID id, String nome, String email, Roles role, String senha, String cpf, LocalDate dataAniversario) {
        super(id, nome, email, role, senha);
        this.cpf = cpf;
        this.dataAniversario = dataAniversario;
    }

    public ClientUser() {
    }


}
