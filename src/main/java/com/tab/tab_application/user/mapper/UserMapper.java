package com.tab.tab_application.user.mapper;

import com.tab.tab_application.user.dto.UserRequestDTO;
import com.tab.tab_application.user.dto.UserResponseDTO;
import com.tab.tab_application.user.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toDto(UserModel user) {
        if (user == null)  return null;

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    public UserModel toEntity(UserRequestDTO dto) {
        if (dto == null) return null;

        UserModel user = new UserModel();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(dto.getPassword()); // TO BE HASHED
        return user;
    }
}
