package com.example.user_service.exceptions;

public class UsuarioNotFoundByCpfException extends RuntimeException {
    public UsuarioNotFoundByCpfException(String cpf){super("Usuário não encontrado com CPF: " + cpf);}
}
