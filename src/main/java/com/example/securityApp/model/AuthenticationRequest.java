package com.example.securityApp.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationRequest {
    String username;
    String password;
}
