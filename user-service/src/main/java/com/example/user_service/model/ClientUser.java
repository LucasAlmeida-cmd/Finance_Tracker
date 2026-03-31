package com.example.user_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table (name = "tb_clintuser")
@Getter
@Setter
@DiscriminatorValue("CLIENTE")
@PrimaryKeyJoinColumn(name = "id_cliente", referencedColumnName = "id")
public class ClientUser extends User {

    @Column(name = "cpf_user", nullable = false, length = 100)
    private String cpf;

    private LocalDate data_aniversario;


    public ClientUser(UUID id, String nome, String email, Roles role, String cpf, LocalDate data_aniversario) {
        super(id, nome, email, role);
        this.data_aniversario = data_aniversario;
        setCpfUser(cpf);
    }

    public ClientUser() {
    }

    public void setCpfUser(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo");
        }
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        if (!validarCpf(cpfNumerico)) {
            throw new IllegalArgumentException("CPF inválido");
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
