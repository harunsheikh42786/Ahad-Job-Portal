package com.ahad.services.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "AUTH-SERVICE", path = "/api/v1/auth")
public interface AuthServiceClient {

    @PostMapping("/validate-token")
    Map<String, Object> validateToken(@RequestHeader("Authorization") String authHeader);

    @PostMapping("/extract-claims")
    Map<String, Object> extractClaims(@RequestHeader("Authorization") String authHeader);

    @PostMapping("/validate-token-body")
    Map<String, Object> validateToken(@RequestBody Map<String, String> request);

    @PostMapping("/extract-claims-body")
    Map<String, Object> extractClaims(@RequestBody Map<String, String> request);
}