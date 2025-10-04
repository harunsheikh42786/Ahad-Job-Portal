package com.ahad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ahad.feign.UserServiceClient;
import com.ahad.helper.ApiResponse;
import com.ahad.feign.CompanyServiceClient;
import com.ahad.dto.AuthRequest;
import com.ahad.dto.AuthResponse;
import com.ahad.util.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserServiceClient userServiceClient;
    private final CompanyServiceClient companyServiceClient;
    private final JwtUtil jwtUtil;

    public AuthResponse authenticate(AuthRequest authRequest) {
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();
        String accountType = authRequest.getAccountType().toUpperCase();

        // log.info("Authenticating {} with email: {}", accountType, email);

        try {
            boolean isVerified = false;
            String userId = null;
            String name = null;

            if ("USER".equals(accountType)) {
                isVerified = verifyWithUserService(email, password);
                if (isVerified) {
                    userId = "user_" + email.hashCode(); // Temporary ID
                    name = extractNameFromEmail(email);
                }
            } else if ("COMPANY".equals(accountType)) {
                isVerified = verifyWithCompanyService(email, password);
                if (isVerified) {
                    userId = "comp_" + email.hashCode(); // Temporary ID
                    name = extractCompanyNameFromEmail(email);
                }
            } else {
                return AuthResponse.builder()
                        .success(false)
                        .message("Invalid account type. Use USER or COMPANY")
                        .build();
            }
            // log.info("DATA: {}", email, password, name, userId);

            if (isVerified) {
                String token = jwtUtil.generateToken(email, accountType, userId, name);
                // log.info("Authentication successful for: {}", email);

                return AuthResponse.builder()
                        .success(true)
                        .token(token)
                        .accountType(accountType)
                        .userId(userId)
                        .name(name)
                        .email(email)
                        .message("Authentication successful")
                        .build();
            } else {
                log.warn("Authentication failed for: {}", email);
                return AuthResponse.builder()
                        .success(false)
                        .message("Invalid email or password")
                        .build();
            }

        } catch (Exception e) {
            log.error("Authentication service error for {}: {}", email, e.getMessage());
            return AuthResponse.builder()
                    .success(false)
                    .message("Authentication service temporarily unavailable")
                    .build();
        }
    }

    private boolean verifyWithUserService(String email, String password) {
        try {
            ResponseEntity<ApiResponse<Boolean>> response = userServiceClient.verifyUser(email, password);
            return response.getStatusCode().is2xxSuccessful() &&
                    response.getBody() != null &&
                    response.getBody().isSuccess() &&
                    Boolean.TRUE.equals(response.getBody().getData());
        } catch (Exception e) {
            log.error("User service call failed: {}", e.getMessage());
            return false;
        }
    }

    private boolean verifyWithCompanyService(String email, String password) {
        try {
            ResponseEntity<ApiResponse<Boolean>> response = companyServiceClient.verifyCompany(email, password);
            return response.getStatusCode().is2xxSuccessful() &&
                    response.getBody() != null &&
                    response.getBody().isSuccess() &&
                    Boolean.TRUE.equals(response.getBody().getData());
        } catch (Exception e) {
            log.error("Company service call failed: {}", e.getMessage());
            return false;
        }
    }

    private String extractNameFromEmail(String email) {
        String username = email.split("@")[0];
        return username.substring(0, 1).toUpperCase() + username.substring(1);
    }

    private String extractCompanyNameFromEmail(String email) {
        String username = email.split("@")[0];

        // Numbers aur special characters hatao
        String cleanName = username.replaceAll("[^a-zA-Z]", "");

        if (cleanName.isEmpty()) {
            cleanName = "Company";
        }

        return cleanName.substring(0, 1).toUpperCase() + cleanName.substring(1);
    }
}