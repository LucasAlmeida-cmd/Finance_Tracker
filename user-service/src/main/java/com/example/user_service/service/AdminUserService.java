package com.example.user_service.service;

import com.example.user_service.DTOs.AdminDTO;
import com.example.user_service.exceptions.AdminNotFoundByIdentificacaoException;
import com.example.user_service.mappers.UserMapper;
import com.example.user_service.model.AdminUser;
import com.example.user_service.model.Roles;
import com.example.user_service.repository.AdminUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminUser adicionarAdmin(@Valid AdminDTO adminDTO){
        AdminUser adminUser = mapper.toEnity(adminDTO);
        adminUser.setRole(Roles.ADMIN);
        adminUser.setIdentificacao(UUID.randomUUID());
        adminUser.setSenha(passwordEncoder.encode(adminDTO.getSenha()));
        return adminUserRepository.save(adminUser);
    }

    public AdminUser atualizarAdmin(UUID identificacao, @Valid AdminDTO adminDTO){
        AdminUser adminBanco = adminUserRepository.findByIdentificacao(identificacao)
                .orElseThrow(()-> new AdminNotFoundByIdentificacaoException(identificacao));

        adminBanco.setEmail(adminDTO.getEmail());
        adminBanco.setNome(adminDTO.getNome());
        return adminUserRepository.save(adminBanco);
    }

    public Object removerAdmin(UUID identificacao){
        AdminUser admin = adminUserRepository.findByIdentificacao(identificacao)
                .orElseThrow(() -> new AdminNotFoundByIdentificacaoException(identificacao));
        adminUserRepository.delete(admin);
        return admin;
    }

    public List<AdminUser> listarAdmins(){
        return adminUserRepository.findAll();
    }


}
