package com.example.user_service.service;

import com.example.user_service.exceptions.UsuarioNotFoundByCpfException;
import com.example.user_service.model.ClientUser;
import com.example.user_service.model.Roles;
import com.example.user_service.repository.ClientUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientUserService {

    @Autowired
    private ClientUserRepository repository;


    public ClientUser adicionar(ClientUser cliente){
        cliente.setRole(Roles.CLIENTE);
        return repository.save(cliente);
    }

    public ClientUser atualizarPorCpf(String cpf, ClientUser cliente){
        ClientUser clienteBanco = repository.findbByCpf(cpf);
        if (clienteBanco == null){
            throw new UsuarioNotFoundByCpfException(cpf);
        }
        clienteBanco.setCpf(cliente.getCpf());
        clienteBanco.setEmail(cliente.getEmail());
        clienteBanco.setData_aniversario(cliente.getData_aniversario());
        clienteBanco.setNome(cliente.getNome());
        return repository.save(clienteBanco);
    }
}
