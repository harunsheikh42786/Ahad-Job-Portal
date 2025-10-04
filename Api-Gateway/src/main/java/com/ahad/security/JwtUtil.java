package com.ahad.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil() {
        // Temporary hardcode - dono services me same
        String secret = "mysecretkey123456789012345678901234";
        String paddedSecret = padSecretKey(secret);
        this.secretKey = Keys.hmacShaKeyFor(paddedSecret.getBytes(StandardCharsets.UTF_8));
        System.out.println("🔐 Gateway Secret: " + secret);
    }

    // Auth Service jaisa same padding method
    private String padSecretKey(String secret) {
        if (secret.length() < 32) {
            StringBuilder padded = new StringBuilder(secret);
            while (padded.length() < 32) {
                padded.append(secret);
            }
            return padded.substring(0, 32);
        } else if (secret.length() > 32) {
            return secret.substring(0, 32);
        }
        return secret;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            log.debug("✅ JWT token validated successfully");
            return true;
        } catch (Exception e) {
            log.error("❌ JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    public String extractAccountType(String token) {
        return extractAllClaims(token).get("accountType", String.class);
    }

    public String extractUserId(String token) {
        return extractAllClaims(token).get("userId", String.class);
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractName(String token) {
        return extractAllClaims(token).get("name", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}