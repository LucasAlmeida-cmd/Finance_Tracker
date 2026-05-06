package com.example.user_service.controller.autenticacao;

import com.example.user_service.DTOs.LoginRequestDTO;
import com.example.user_service.DTOs.UserResponse;
import com.example.user_service.config.TokenService;
import com.example.user_service.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class AutenticacaoController {

    @Autowired
    @Lazy
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/auth")
    public ResponseEntity efetuarLogin(@RequestBody @Valid LoginRequestDTO dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha());
        var authentication = manager.authenticate(authenticationToken);
        var usuario = (User) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(usuario);

        ResponseCookie cookie = ResponseCookie.from("accessToken", tokenJWT)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(86400)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new UserResponse(usuario.getEmail(), usuario.getNome()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
