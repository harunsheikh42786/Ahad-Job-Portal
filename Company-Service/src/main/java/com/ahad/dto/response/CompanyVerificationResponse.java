package com.ahad.dto.response;

import java.util.UUID;

import lombok.Data;

@Data
public class CompanyVerificationResponse {
    private boolean verified;
    private UUID id;
    private String email;
    private String role;
}