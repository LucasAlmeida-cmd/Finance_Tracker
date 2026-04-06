package com.example.user_service.controller;

import com.example.user_service.model.AdminUser;
import com.example.user_service.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    AdminUserService service;

    @PostMapping
    public ResponseEntity<AdminUser> addAdmin(@RequestBody AdminUser adminUser){
        AdminUser admin = service.adicionarAdmin(adminUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(admin);
    }

    @GetMapping
    public ResponseEntity<List<AdminUser>> listAdmin(){
        return ResponseEntity.ok(service.listarAdmins());
    }

    @PutMapping("/{identificacao}")
    public ResponseEntity<AdminUser> updateAdmin(@PathVariable UUID identificacao,@RequestBody AdminUser adminUser){
        return ResponseEntity.ok(service.atualizarAdmin(identificacao, adminUser));
    }

    @DeleteMapping("/{identificacao}")
    public ResponseEntity<AdminUser> deleteAdmin(@PathVariable UUID identificacao){
        service.removerAdmin(identificacao);
        return ResponseEntity.noContent().build();
    }
}
