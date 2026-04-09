package com.example.user_service.controller.autenticacao;

import com.example.user_service.DTOs.LoginRequestDTO;
import com.example.user_service.DTOs.TokenResponseDTO;
import com.example.user_service.config.TokenService;
import com.example.user_service.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    @Lazy
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid LoginRequestDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenResponseDTO(tokenJWT));
    }
}
