package com.example.user_service.service;

import com.example.user_service.exceptions.AdminNotFoundByIdentificaçãoException;
import com.example.user_service.exceptions.AdminNotFoundByIndentificacaoException;
import com.example.user_service.model.AdminUser;
import com.example.user_service.model.ClientUser;
import com.example.user_service.model.Roles;
import com.example.user_service.repository.AdminUserRepository;
import com.example.user_service.repository.ClientUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private ClientUserRepository clientUserRepository;

    /// Admin
    public AdminUser adicionarAdmin(AdminUser adminUser){
        adminUser.setRole(Roles.ADMIN);
        return adminUserRepository.save(adminUser);
    }

    public AdminUser atualizarAdmin(UUID identificação, AdminUser adminUser){
        AdminUser adminBanco = adminUserRepository.findByIdentificacao(identificação)
                .orElseThrow(()-> new AdminNotFoundByIdentificaçãoException(identificação));

        adminBanco.setEmail(adminUser.getEmail());
        adminBanco.setNome(adminUser.getNome());
        return adminUserRepository.save(adminBanco);
    }

    public Object removerAdmin(UUID identificacao){
        AdminUser admin = adminUserRepository.findByIdentificacao(identificacao)
                .orElseThrow(() -> new AdminNotFoundByIdentificaçãoException(identificacao));
        adminUserRepository.delete(admin);
        return admin;
    }

    public List<AdminUser> listarAdmins(){
        return adminUserRepository.findAll();
    }


    /// Clients
    public ClientUser adicionarClient(ClientUser clientUser){
        clientUser.setRole(Roles.CLIENTE);
        return clientUserRepository.save(clientUser);
    }



}
