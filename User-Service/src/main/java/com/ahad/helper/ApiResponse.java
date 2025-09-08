package com.ahad.helper;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private int status;
    private String errorCode; // e.g., USER_ALREADY_EXISTS, VALIDATION_ERROR
    private String errorDetails; // e.g., "Email field is required"
}
