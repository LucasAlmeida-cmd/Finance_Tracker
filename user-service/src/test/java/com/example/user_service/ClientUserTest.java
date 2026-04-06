package com.example.user_service;

import com.example.user_service.exceptions.CpfInvalidException;
import com.example.user_service.model.ClientUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClientUserTest {

    @Test
    @DisplayName("Não deve aceitar CPF inválido")
    void deveLancarExcecaoCpfInvalido() {
        ClientUser client = new ClientUser();
        assertThrows(CpfInvalidException.class, () -> client.setCpf("11111111111"));
    }

    @Test
    @DisplayName("Deve formatar e salvar CPF válido")
    void deveSalvarCpfValido() {
        ClientUser client = new ClientUser();
        client.setCpf("099.568.410-30");
        assertEquals("09956841030", client.getCpf());
    }
}
