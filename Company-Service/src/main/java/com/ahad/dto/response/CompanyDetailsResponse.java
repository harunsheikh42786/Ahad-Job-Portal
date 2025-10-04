package com.ahad.dto.response;

import java.util.UUID;

import lombok.Data;

@Data
public class CompanyDetailsResponse {
    private UUID id;
    private String email;
    private String role;
    private boolean isOpen;
}