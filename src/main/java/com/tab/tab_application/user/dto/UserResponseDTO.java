package com.tab.tab_application.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
}
