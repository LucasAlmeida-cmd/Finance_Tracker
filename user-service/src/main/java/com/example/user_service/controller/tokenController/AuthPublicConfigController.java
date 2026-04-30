package com.example.user_service.controller.tokenController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
public class AuthPublicConfigController {

    @Autowired
    private RSAPublicKey publicKey;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getPublicKey() {
        return Map.of(
                "keys", List.of(
                        Map.of(
                                "kty", "RSA",
                                "n", Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getModulus().toByteArray()),
                                "e", Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getPublicExponent().toByteArray()),
                                "alg", "RS256",
                                "use", "sig"
                        )
                )
        );
    }
}