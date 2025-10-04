package com.ahad.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private boolean success;
    private String token;
    private String accountType;
    private String userId;
    private String message;
    private String name;
    private String email;
}