package com.ahad.controller;

import com.ahad.dto.LoginRequest;
import com.ahad.dto.LoginResponse;
import com.ahad.exception.AuthException;
import com.ahad.feign.UserServiceClient;
import com.ahad.helper.ApiResponse;
import com.ahad.feign.CompanyServiceClient;
import com.ahad.security.JwtUtil;

import feign.FeignException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserServiceClient userClient;

    @Autowired
    private CompanyServiceClient companyClient;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            ApiResponse<Boolean> apiResponse = null;

            if ("USER".equalsIgnoreCase(request.getRole())) {
                apiResponse = userClient.verifyUser(request.getUsername(), request.getPassword());
            } else if ("COMPANY".equalsIgnoreCase(request.getRole())) {
                apiResponse = companyClient.verifyCompany(request.getUsername(), request.getPassword());
            } else {
                throw new AuthException("Invalid role. Must be USER or COMPANY");
            }

            if (apiResponse == null || apiResponse.getData() == null || !apiResponse.getData()) {
                throw new AuthException("Invalid credentials");
            }

            String token = jwtUtil.generateToken(request.getUsername(), request.getRole());
            LoginResponse loginResponse = new LoginResponse(token, request.getRole(), jwtUtil.getExpiration());

            return ResponseEntity.ok(loginResponse);

        } catch (FeignException e) {
            throw new AuthException("Authentication service unavailable");
        }
    }

}
