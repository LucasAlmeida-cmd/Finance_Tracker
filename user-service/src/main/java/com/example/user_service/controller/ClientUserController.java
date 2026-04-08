package com.example.user_service.controller;

import com.example.user_service.DTOs.ClientDTO;
import com.example.user_service.DTOs.ClientUpdateDTO;
import com.example.user_service.model.ClientUser;
import com.example.user_service.service.ClientUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ClientUserController {

    @Autowired
    ClientUserService service;

    @PostMapping
    public ResponseEntity<ClientUser> addClient(@RequestBody @Valid ClientDTO cliente){
        ClientUser client = service.adicionar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @GetMapping
    public ResponseEntity<List<ClientUser>> retornaClient(){
        return ResponseEntity.ok(service.listarClients());
    }

    @PutMapping("/{cpf}")
    @PreAuthorize("hasRole('ADMIN') or #cpf == authentication.principal.cpf")
    public ResponseEntity<ClientUser> updateClient(@PathVariable String cpf, @RequestBody @Valid ClientUpdateDTO usuario){
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        return ResponseEntity.ok(service.atualizarPorCpf(cpfLimpo, usuario));
    }

    @DeleteMapping("/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClient(@PathVariable String cpf){
        service.removerClientPorCpf(cpf);
        return ResponseEntity.noContent().build();
    }
}
