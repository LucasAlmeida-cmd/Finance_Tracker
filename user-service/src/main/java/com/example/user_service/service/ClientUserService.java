package com.example.user_service.service;

import com.example.user_service.DTOs.ClientDTO;
import com.example.user_service.DTOs.ClientUpdateDTO;
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
        String cpfNumerico = clientDTO.getCpf().replaceAll("[^0-9]", "");
        ClientUser cliente = mapper.fromClientDTO(clientDTO);
        cliente.setRole(Roles.CLIENTE);
        cliente.setCpf(cpfNumerico);
        cliente.setSenha(passwordEncoder.encode(clientDTO.getSenha()));
        return repository.save(cliente);
    }

    public ClientUser atualizarPorCpf(String cpf, @Valid ClientUpdateDTO cliente) {
        ClientUser clienteBanco = repository.findByCpf(cpf)
                .orElseThrow(() -> new UsuarioNotFoundByCpfException(cpf));
        clienteBanco.setCpf(cpf);
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
