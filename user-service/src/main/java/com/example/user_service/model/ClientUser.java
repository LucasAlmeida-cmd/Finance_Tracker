package com.example.user_service.model;

import com.example.user_service.exceptions.CpfInvalidException;
import com.example.user_service.exceptions.CpfNotNullException;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private String cpf;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name = "data_aniversario_usuario", nullable = false)
    private LocalDate dataAniversario;



    public ClientUser(UUID id, String nome, String email, Roles role, String cpf, LocalDate dataAniversario) {
        super(id, nome, email, role);
        this.dataAniversario = dataAniversario;
        setCpfUser(cpf);
    }

    public ClientUser() {
    }

    public void setCpfUser(String cpf) {
        if (cpf == null) {
            throw new CpfNotNullException();
        }
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        if (!validarCpf(cpfNumerico)) {
            throw new CpfInvalidException();
        }
        this.cpf = cpfNumerico;
    }

    private static boolean validarCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 > 9) digito1 = 0;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 > 9) digito2 = 0;

        return (cpf.charAt(9) - '0' == digito1) && (cpf.charAt(10) - '0' == digito2);
    }
}
