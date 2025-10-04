// package com.ahad.security;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.core.Ordered;
// import org.springframework.core.io.buffer.DataBuffer;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.http.server.reactive.ServerHttpResponse;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;
// import reactor.core.publisher.Mono;

// import java.nio.charset.StandardCharsets;
// import java.util.List;

// @Slf4j
// @Component
// @RequiredArgsConstructor
// public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

//     private final JwtUtil jwtUtil;

//     // Public routes jo JWT check nahi karenge
//     private static final List<String> PUBLIC_PATHS = List.of(
//             "/api/v1/auth/",
//             "/authenticate",
//             "/api/v1/register/",
//             "/eureka/",
//             "/actuator/",
//             "/favicon.ico");

//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//         ServerHttpRequest request = exchange.getRequest();
//         String path = request.getURI().getPath();
//         String method = request.getMethod().name();

//         log.debug("🔐 Gateway Filter - {} {}", method, path);

//         // 1. Check if this is a public route
//         if (isPublicRoute(path, method)) {
//             log.debug("✅ Public route access: {}", path);
//             return chain.filter(exchange);
//         }

//         // 2. Get Authorization header
//         String authHeader = getAuthHeader(request);
//         if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//             log.warn("❌ Missing or invalid Authorization header for: {}", path);
//             return unauthorizedResponse(exchange, "Missing or invalid Authorization token");
//         }

//         // 3. Extract and validate JWT token
//         String token = authHeader.substring(7).trim();
//         if (token.isEmpty() || !jwtUtil.validateToken(token)) {
//             log.warn("❌ Invalid JWT token for: {}", path);
//             return unauthorizedResponse(exchange, "Invalid or expired token");
//         }

//         // 4. Add user headers to the request
//         try {
//             ServerHttpRequest mutatedRequest = addUserHeaders(request, token);
//             log.debug("✅ JWT validated successfully for: {}", path);
//             return chain.filter(exchange.mutate().request(mutatedRequest).build());

//         } catch (Exception e) {
//             log.error("❌ Error processing JWT token: {}", e.getMessage());
//             return unauthorizedResponse(exchange, "Token processing error");
//         }
//     }

//     private boolean isPublicRoute(String path, String method) {
//         // Check exact public paths first
//         for (String publicPath : PUBLIC_PATHS) {
//             if (path.startsWith(publicPath)) {
//                 return true;
//             }
//         }

//         // Public GET routes for jobs and categories
//         if ("GET".equals(method)) {
//             return isPublicGetRoute(path);
//         }

//         return false;
//     }

//     private boolean isPublicGetRoute(String path) {
//         // Public job listings
//         if (path.startsWith("/api/v1/jobs")) {
//             // Allow public access to job listings, search, and details
//             return path.equals("/api/v1/jobs") ||
//                     path.startsWith("/api/v1/jobs/search") ||
//                     path.matches("/api/v1/jobs/\\d+") || // Job details by ID
//                     path.matches("/api/v1/jobs/[a-zA-Z0-9-]+"); // Job details by slug
//         }

//         // Public categories
//         if (path.startsWith("/api/v1/categories")) {
//             return true;
//         }

//         // Health checks
//         if (path.equals("/health") || path.startsWith("/actuator/health")) {
//             return true;
//         }

//         return false;
//     }

//     private String getAuthHeader(ServerHttpRequest request) {
//         List<String> authHeaders = request.getHeaders().get("Authorization");
//         return (authHeaders != null && !authHeaders.isEmpty()) ? authHeaders.get(0) : null;
//     }

//     private ServerHttpRequest addUserHeaders(ServerHttpRequest request, String token) {
//         try {
//             String accountType = jwtUtil.extractAccountType(token);
//             String email = jwtUtil.extractEmail(token);
//             String userId = jwtUtil.extractUserId(token);
//             String name = jwtUtil.extractName(token);

//             log.debug("👤 Extracted user info - Type: {}, Email: {}, ID: {}", accountType, email, userId);

//             ServerHttpRequest.Builder requestBuilder = request.mutate()
//                     .header("X-Account-Type", accountType)
//                     .header("X-User-Email", email)
//                     .header("X-User-ID", userId)
//                     .header("X-User-Name", name);

//             // Additional headers based on account type
//             if ("USER".equals(accountType)) {
//                 requestBuilder.header("X-User-Role", "USER");
//             } else if ("COMPANY".equals(accountType)) {
//                 requestBuilder.header("X-Company-Role", "COMPANY");
//                 requestBuilder.header("X-Company-ID", userId); // For company services
//             }

//             return requestBuilder.build();

//         } catch (Exception e) {
//             log.error("❌ Error extracting user info from token: {}", e.getMessage());
//             throw new RuntimeException("Failed to extract user information from token");
//         }
//     }

//     private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
//         ServerHttpResponse response = exchange.getResponse();
//         response.setStatusCode(HttpStatus.UNAUTHORIZED);
//         response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
//         response.getHeaders().set("X-Auth-Error", "true");

//         String responseBody = String.format(
//                 "{\"success\": false, \"message\": \"%s\", \"error\": \"UNAUTHORIZED\", \"status\": 401, \"path\": \"%s\"}",
//                 message,
//                 exchange.getRequest().getURI().getPath());

//         DataBuffer buffer = response.bufferFactory()
//                 .wrap(responseBody.getBytes(StandardCharsets.UTF_8));

//         return response.writeWith(Mono.just(buffer));
//     }

//     @Override
//     public int getOrder() {
//         return Ordered.HIGHEST_PRECEDENCE; // Execute before other filters
//     }
// }
package com.ahad.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    // Public routes jo JWT check nahi karenge
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/v1/auth/",
            "/authenticate",
            "/api/v1/register/",
            "/eureka/",
            "/actuator/",
            "/favicon.ico");

    // USER-specific routes
    private static final Set<String> USER_ONLY_PATHS = Set.of(
            "/api/v1/users/",
            "/api/v1/job-histories/",
            "/api/v1/addresses/",
            "/api/v1/achievements/",
            "/api/v1/educations/",
            "/api/v1/user-info/");

    // COMPANY-specific routes
    private static final Set<String> COMPANY_ONLY_PATHS = Set.of(
            "/api/v1/companies/",
            "/api/v1/jobs/",
            "/api/v1/job-applications/",
            "/api/v1/company-information/", // POST, PUT, DELETE operations
            "/api/v1/company-addresses/");

    // ADMIN routes (agar chahiye to)
    private static final Set<String> ADMIN_ONLY_PATHS = Set.of(
            "/api/v1/admin/",
            "/api/v1/dashboard/admin/");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        String method = request.getMethod().name();

        log.debug("🔐 Gateway Filter - {} {}", method, path);

        // 1. Check if this is a public route
        if (isPublicRoute(path, method)) {
            log.debug("✅ Public route access: {}", path);
            return chain.filter(exchange);
        }

        // 2. Get Authorization header
        String authHeader = getAuthHeader(request);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("❌ Missing or invalid Authorization header for: {}", path);
            return unauthorizedResponse(exchange, "Missing or invalid Authorization token");
        }

        // 3. Extract and validate JWT token
        String token = authHeader.substring(7).trim();
        if (token.isEmpty() || !jwtUtil.validateToken(token)) {
            log.warn("❌ Invalid JWT token for: {}", path);
            return unauthorizedResponse(exchange, "Invalid or expired token");
        }

        // 4. Check role-based access
        String accountType = jwtUtil.extractAccountType(token);
        if (!hasAccess(accountType, path, method)) {
            log.warn("🚫 Access denied for {} to: {}", accountType, path);
            return forbiddenResponse(exchange, "Access denied. Insufficient permissions.");
        }

        // 5. Add user headers to the request
        try {
            ServerHttpRequest mutatedRequest = addUserHeaders(request, token);
            log.debug("✅ Access granted - Type: {}, Path: {}", accountType, path);
            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (Exception e) {
            log.error("❌ Error processing JWT token: {}", e.getMessage());
            return unauthorizedResponse(exchange, "Token processing error");
        }
    }

    private boolean hasAccess(String accountType, String path, String method) {
        // Check USER-only routes
        if (USER_ONLY_PATHS.stream().anyMatch(path::startsWith)) {
            return "USER".equals(accountType);
        }

        // Check COMPANY-only routes
        if (COMPANY_ONLY_PATHS.stream().anyMatch(path::startsWith)) {
            return "COMPANY".equals(accountType);
        }

        // Check ADMIN-only routes
        if (ADMIN_ONLY_PATHS.stream().anyMatch(path::startsWith)) {
            return "ADMIN".equals(accountType);
        }

        // Special cases for job routes
        if (path.startsWith("/api/v1/jobs")) {
            return hasJobRouteAccess(accountType, path, method);
        }

        // Default: allow access to other authenticated routes
        return true;
    }

    private boolean hasJobRouteAccess(String accountType, String path, String method) {
        // Public GET routes - sabko allow
        if ("GET".equals(method)) {
            return true;
        }

        // Job creation/modification - only COMPANY
        if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method)) {
            return "COMPANY".equals(accountType);
        }

        return true;
    }

    private boolean isPublicRoute(String path, String method) {
        // Check exact public paths first
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) {
                return true;
            }
        }

        // Public GET routes for jobs and categories
        if ("GET".equals(method)) {
            return isPublicGetRoute(path);
        }

        return false;
    }

    private boolean isPublicGetRoute(String path) {
        // Public job listings
        if (path.startsWith("/api/v1/jobs")) {
            return path.equals("/api/v1/jobs") ||
                    path.startsWith("/api/v1/jobs/search") ||
                    path.matches("/api/v1/jobs/\\d+") ||
                    path.matches("/api/v1/jobs/[a-zA-Z0-9-]+");
        }

        // Public categories
        if (path.startsWith("/api/v1/categories")) {
            return true;
        }

        // Health checks
        return path.equals("/health") || path.startsWith("/actuator/health");
    }

    private String getAuthHeader(ServerHttpRequest request) {
        List<String> authHeaders = request.getHeaders().get("Authorization");
        return (authHeaders != null && !authHeaders.isEmpty()) ? authHeaders.get(0) : null;
    }

    private ServerHttpRequest addUserHeaders(ServerHttpRequest request, String token) {
        try {
            String accountType = jwtUtil.extractAccountType(token);
            String email = jwtUtil.extractEmail(token);
            String userId = jwtUtil.extractUserId(token);
            String name = jwtUtil.extractName(token);

            log.debug("👤 Extracted user info - Type: {}, Email: {}, ID: {}", accountType, email, userId);

            ServerHttpRequest.Builder requestBuilder = request.mutate()
                    .header("X-Account-Type", accountType)
                    .header("X-User-Email", email)
                    .header("X-User-ID", userId)
                    .header("X-User-Name", name);

            // Additional headers based on account type
            if ("USER".equals(accountType)) {
                requestBuilder.header("X-User-Role", "USER");
            } else if ("COMPANY".equals(accountType)) {
                requestBuilder.header("X-Company-Role", "COMPANY");
                requestBuilder.header("X-Company-ID", userId);
            }

            return requestBuilder.build();

        } catch (Exception e) {
            log.error("❌ Error extracting user info from token: {}", e.getMessage());
            throw new RuntimeException("Failed to extract user information from token");
        }
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String responseBody = String.format(
                "{\"success\": false, \"message\": \"%s\", \"error\": \"UNAUTHORIZED\", \"status\": 401, \"path\": \"%s\"}",
                message,
                exchange.getRequest().getURI().getPath());

        DataBuffer buffer = response.bufferFactory()
                .wrap(responseBody.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }

    private Mono<Void> forbiddenResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String responseBody = String.format(
                "{\"success\": false, \"message\": \"%s\", \"error\": \"FORBIDDEN\", \"status\": 403, \"path\": \"%s\"}",
                message,
                exchange.getRequest().getURI().getPath());

        DataBuffer buffer = response.bufferFactory()
                .wrap(responseBody.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}