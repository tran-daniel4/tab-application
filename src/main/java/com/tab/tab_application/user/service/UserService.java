package com.tab.tab_application.user.service;

import com.tab.tab_application.user.dto.UserRequestDTO;
import com.tab.tab_application.user.dto.UserResponseDTO;
import com.tab.tab_application.user.mapper.UserMapper;
import com.tab.tab_application.user.model.UserModel;
import com.tab.tab_application.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO createUser(UserRequestDTO userDto, String rawPassword) {
        UserModel user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(rawPassword));
        UserModel savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    public UserResponseDTO updateUser(Long id, Map<String, Object> updates) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID" + id + " not found"));
        updates.forEach((field, value) -> {
            switch(field) {
                case "name" -> user.setName((String) value);
                case "email" -> user.setEmail((String) value);
                case "phoneNumber" -> user.setPhoneNumber((String) value);
                case "password" -> user.setPassword((String) value);
            }
        });
        UserModel updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

}
