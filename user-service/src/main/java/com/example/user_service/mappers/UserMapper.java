package com.example.user_service.mappers;

import com.example.user_service.DTOs.AdminDTO;
import com.example.user_service.DTOs.ClientDTO;
import com.example.user_service.model.AdminUser;
import com.example.user_service.model.ClientUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ClientUser toEntity(ClientDTO dto);
    AdminUser toEnity(AdminDTO dto);
}