package com.example.user_service.exceptions;

public class CpfNotNullException extends RuntimeException {
    public CpfNotNullException(){super("Campo CPF não pode estar vazio.");}
}
