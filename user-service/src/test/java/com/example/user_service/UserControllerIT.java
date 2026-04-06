package com.example.user_service;

import com.example.user_service.model.ClientUser;
import com.example.user_service.model.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // IMPORTANTE
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType; // O correto é org.springframework.http
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate; // Faltava esse cara

// Imports estáticos corrigidos (atenção aqui!)
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um ClientUser com sucesso via API")
    void deveCriarCliente() throws Exception {
        ClientUser novoCliente = new ClientUser();
        novoCliente.setNome("Thiago");
        novoCliente.setEmail("thiago@email.com");
        novoCliente.setRole(Roles.CLIENTE);
        novoCliente.setCpf("015.234.500-02");
        novoCliente.setDataAniversario(LocalDate.of(1995, 5, 20));

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoCliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Thiago"))
                .andExpect(jsonPath("$.cpf").value("01523450002"));
    }

    @Test
    @DisplayName("Deve retornar erro 400 ao enviar CPF inválido")
    void deveRetornarErroCpfInvalido() throws Exception {
        String jsonErro = """
            {
                "nome": "Erro",
                "email": "erro@email.com",
                "cpf": "123.123.132-22",
                "dataAniversario": "10/10/1990",
                "role": "CLIENTE"
            }
        """;

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonErro))
                .andExpect(status().isBadRequest());
    }
}