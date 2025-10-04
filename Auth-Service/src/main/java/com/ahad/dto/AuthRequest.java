package com.ahad.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AuthRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Valid email address is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{6,}$", message = "Password must be at least 6 characters with letters and numbers")
    private String password;

    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "^(USER|COMPANY)$", message = "Account type must be USER or COMPANY")
    private String accountType;
}