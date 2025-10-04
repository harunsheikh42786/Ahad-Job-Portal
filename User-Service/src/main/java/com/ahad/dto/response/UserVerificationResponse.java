package com.ahad.dto.response;

import lombok.Data;

@Data
public class UserVerificationResponse {
    private boolean verified;
    private String id;
    private String email;
    private String role;
}