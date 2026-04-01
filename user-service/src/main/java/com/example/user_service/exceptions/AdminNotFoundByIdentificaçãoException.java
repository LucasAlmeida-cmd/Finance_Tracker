package com.example.user_service.exceptions;

import java.util.UUID;

public class AdminNotFoundByIdentificaçãoException extends RuntimeException{
    public AdminNotFoundByIdentificaçãoException(UUID identificação){super("Administrador não encontrado com essa identificação: "+ identificação);}
}
