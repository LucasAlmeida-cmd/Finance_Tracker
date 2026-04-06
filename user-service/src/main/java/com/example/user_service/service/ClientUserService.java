package com.example.user_service.service;

import com.example.user_service.DTOs.ClientDTO;
import com.example.user_service.exceptions.UsuarioNotFoundByCpfException;
import com.example.user_service.mappers.UserMapper;
import com.example.user_service.model.ClientUser;
import com.example.user_service.model.Roles;
import com.example.user_service.repository.ClientUserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUserService {

    @Autowired
    private ClientUserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper mapper;


    public ClientUser adicionar(@Valid ClientDTO clientDTO){
        ClientUser cliente = mapper.toEntity(clientDTO);
        cliente.setRole(Roles.CLIENTE);
        cliente.setSenha(passwordEncoder.encode(clientDTO.getSenha()));
        return repository.save(cliente);
    }

    public ClientUser atualizarPorCpf(String cpf,@Valid ClientDTO cliente) {
        ClientUser clienteBanco = repository.findByCpf(cpf)
                .orElseThrow(() -> new UsuarioNotFoundByCpfException(cpf));
        clienteBanco.setCpf(cliente.getCpf());
        clienteBanco.setEmail(cliente.getEmail());
        clienteBanco.setDataAniversario(cliente.getDataAniversario());
        clienteBanco.setNome(cliente.getNome());

        return repository.save(clienteBanco);
    }


    @Transactional
    public void removerClientPorCpf(String cpf){
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");
        ClientUser clienteBanco = repository.findByCpf(cpfNumerico)
                .orElseThrow(()-> new UsuarioNotFoundByCpfException(cpf));
        repository.deleteByCpf(cpfNumerico);
    }


    public List<ClientUser> listarClients() {
        return repository.findAll();
    }
}
