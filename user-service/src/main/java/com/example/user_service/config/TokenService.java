package com.example.user_service.config;

import com.example.user_service.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Autowired
    private RSAPrivateKey privateKey;

    @Autowired
    private RSAPublicKey publicKey;

    public String gerarToken(User usuario) {
        Instant agora = Instant.now();
        Instant expiracao = getDataExpiracao().toInstant(ZoneOffset.of("-03:00"));

        return Jwts.builder()
                .setIssuer("API")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(Date.from(agora))
                .setExpiration(Date.from(expiracao))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public String getSubject(String tokenJWT) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(tokenJWT)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    private LocalDateTime getDataExpiracao() {
        return LocalDateTime.now().plusHours(2);
    }
}