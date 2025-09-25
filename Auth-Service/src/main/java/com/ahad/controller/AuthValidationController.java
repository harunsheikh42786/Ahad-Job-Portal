package com.ahad.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahad.security.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthValidationController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(
                    Map.of("valid", false, "message", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        boolean isValid = jwtUtil.validateToken(token);

        Map<String, Object> response = Map.of(
                "valid", isValid,
                "message", isValid ? "Token is valid" : "Token is invalid");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/extract-claims")
    public ResponseEntity<Map<String, Object>> extractClaims(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Missing or invalid Authorization header"));
        }

        try {
            String token = authHeader.substring(7);
            Map<String, Object> claims = jwtUtil.extractClaims(token);
            return ResponseEntity.ok(claims);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Invalid token: " + e.getMessage()));
        }
    }

    // ✅ Alternative: Request body approach
    @PostMapping("/validate-token-body")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        if (token == null || token.isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("valid", false, "message", "Token is required"));
        }

        boolean isValid = jwtUtil.validateToken(token);

        Map<String, Object> response = Map.of(
                "valid", isValid,
                "message", isValid ? "Token is valid" : "Token is invalid");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/extract-claims-body")
    public ResponseEntity<Map<String, Object>> extractClaims(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        if (token == null || token.isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Token is required"));
        }

        try {
            Map<String, Object> claims = jwtUtil.extractClaims(token);
            return ResponseEntity.ok(claims);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Invalid token: " + e.getMessage()));
        }
    }
}