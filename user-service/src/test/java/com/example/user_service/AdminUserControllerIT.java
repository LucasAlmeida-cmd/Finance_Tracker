package com.example.user_service;

import com.example.user_service.model.AdminUser;
import com.example.user_service.model.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminUserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um AdminUser com sucesso")
    void deveCriarAdmin() throws Exception {

        AdminUser admin = new AdminUser();
        admin.setNome("Admin Supremo");
        admin.setEmail("admin@empresa.com");
        admin.setRole(Roles.ADMIN);

        UUID identificacaoRandom = UUID.randomUUID();
        admin.setIdentificacao(identificacaoRandom);

        mockMvc.perform(post("/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Admin Supremo"))
                .andExpect(jsonPath("$.identificacao").exists())
                .andExpect(jsonPath("$.identificacao").value(notNullValue()));
    }
}