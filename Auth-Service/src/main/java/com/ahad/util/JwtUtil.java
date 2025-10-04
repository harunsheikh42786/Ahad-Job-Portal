package com.ahad.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMs;

    public JwtUtil() {
        // Temporary hardcode - dono services me same
        String secret = "mysecretkey123456789012345678901234";
        String paddedSecret = padSecretKey(secret);
        this.secretKey = Keys.hmacShaKeyFor(paddedSecret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = 3600 * 1000; // 1 hour in milliseconds - YEH ADD KARO
        // System.out.println("🔐 Auth Service Secret: " + secret);
        // System.out.println("🔐 Auth Service Expiration: " + expirationMs + "ms");
    }

    // Secret key ko 32 characters ka banaye
    private String padSecretKey(String secret) {
        if (secret.length() < 32) {
            // Pad with repeated characters to make it 32 chars
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

    public String generateToken(String email, String accountType, String userId, String name) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("accountType", accountType);
        claims.put("userId", userId);
        claims.put("name", name);
        claims.put("email", email);

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT token expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (Exception e) {
            log.error("JWT validation error: {}", e.getMessage());
        }
        return false;
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