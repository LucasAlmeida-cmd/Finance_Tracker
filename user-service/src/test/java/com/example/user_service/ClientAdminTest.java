package com.example.user_service;

import com.example.user_service.model.AdminUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientAdminTest {

    @Test
    @DisplayName("Deve instanciar um AdminUser corretamente")
    void deveCriarObjetoAdmin() {
        UUID idRef = UUID.randomUUID();
        AdminUser admin = new AdminUser();
        admin.setNome("Lucas");
        admin.setIdentificacao(idRef);

        assertEquals("Lucas", admin.getNome());
        assertEquals(idRef, admin.getIdentificacao());
    }
}
