package com.example.user_service.mappers;

import com.example.user_service.DTOs.ClientCadastroDTO;
import com.example.user_service.model.ClientUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ClientUser toEntity(ClientCadastroDTO dto);
}