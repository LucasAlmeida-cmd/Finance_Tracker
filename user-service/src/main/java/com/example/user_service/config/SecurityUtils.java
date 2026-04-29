package com.example.user_service.config;

import org.springframework.stereotype.Component;

@Component("securityUtils")
public class SecurityUtils {
    public boolean cpfConfere(String cpfUrl, String cpfAutenticado) {
        if (cpfUrl == null || cpfAutenticado == null) return false;
        String cpfUrlLimpo = cpfUrl.replaceAll("\\D", "");
        String cpfAutenticadoLimpo = cpfAutenticado.replaceAll("\\D", "");

        return cpfUrlLimpo.equals(cpfAutenticadoLimpo);
    }
}