package com.tab.tab_application.user.controller;

import com.tab.tab_application.user.dto.UserRequestDTO;
import com.tab.tab_application.user.dto.UserResponseDTO;
import com.tab.tab_application.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userDTO) {
        UserResponseDTO createUser = userService.createUser(userDTO, userDTO.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // Returns status code 204 No content
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        UserResponseDTO updatedUser = userService.updateUser(id, updates);
        return ResponseEntity.ok(updatedUser);
    }
}
