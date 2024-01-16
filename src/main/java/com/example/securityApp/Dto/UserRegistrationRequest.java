package com.example.securityApp.Dto;

import com.example.securityApp.model.Role;
import lombok.Data;

@Data
public class UserRegistrationRequest {

    private String username;
    private String password;
    private Role role;
}
