package com.ahad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ahad.dto.AuthRequest;
import com.ahad.dto.AuthResponse;
import com.ahad.service.AuthenticationService;
import com.ahad.util.JwtUtil;
import com.ahad.helper.ApiResponse;
import com.ahad.helper.ApiVersion;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping(ApiVersion.V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthenticationService authenticationService;
        private final JwtUtil jwtUtil;

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<AuthResponse>> login(
                        @Valid @RequestBody AuthRequest authRequest,
                        BindingResult bindingResult) {

                if (bindingResult.hasErrors()) {
                        String errorMessage = (bindingResult.getFieldError() != null)
                                        ? bindingResult.getFieldError().getDefaultMessage()
                                        : "Unknown validation error";
                        return ResponseEntity.badRequest().body(
                                        ApiResponse.<AuthResponse>builder()
                                                        .success(false)
                                                        .message("Validation failed: " + errorMessage)
                                                        .data(null)
                                                        .timestamp(LocalDateTime.now())
                                                        .status(HttpStatus.BAD_REQUEST.value())
                                                        .errorCode("VALIDATION_ERROR")
                                                        .errorDetails(errorMessage)
                                                        .build());
                }

                AuthResponse authResponse = authenticationService.authenticate(authRequest);

                // YEH CHANGE KARO - authResponse ke success status ko follow karo
                HttpStatus status = authResponse.isSuccess() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

                ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                                .success(authResponse.isSuccess()) // ← YEH IMPORTANT CHANGE
                                .message(authResponse.isSuccess() ? "Login successful" : authResponse.getMessage())
                                .data(authResponse.isSuccess() ? authResponse : null) // ← Success par hi data
                                .timestamp(LocalDateTime.now())
                                .status(status.value())
                                .errorCode(authResponse.isSuccess() ? null : "AUTH_FAILED")
                                .errorDetails(authResponse.isSuccess() ? null : authResponse.getMessage())
                                .build();

                return ResponseEntity.status(status).body(apiResponse);
        }

        @PostMapping("/validate")
        public ResponseEntity<ApiResponse<Boolean>> validateToken(@RequestParam String token) {
                boolean isValid = jwtUtil.validateToken(token);

                HttpStatus status = isValid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
                ApiResponse<Boolean> apiResponse = ApiResponse.<Boolean>builder()
                                .success(isValid)
                                .message(isValid ? "Token is valid" : "Invalid token")
                                .data(isValid)
                                .timestamp(LocalDateTime.now())
                                .status(status.value())
                                .errorCode(isValid ? null : "INVALID_TOKEN")
                                .errorDetails(isValid ? null : "Token validation failed")
                                .build();

                return ResponseEntity.status(status).body(apiResponse);
        }
}