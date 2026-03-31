package com.example.user_service.controller;

import com.example.user_service.model.ClientUser;
import com.example.user_service.service.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class ClientUserController {

    @Autowired
    ClientUserService service;

    @PostMapping
    public ResponseEntity<ClientUser> addClient(@RequestBody ClientUser cliente){
        ClientUser client = service.adicionar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @RequestMapping("{/cpf}")
    public ResponseEntity<ClientUser> updateClient(@PathVariable String cpf, @RequestBody ClientUser usuario){
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        return ResponseEntity.ok(service.atualizarPorCpf(cpfLimpo, usuario));
    }
}
