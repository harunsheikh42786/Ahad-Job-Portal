package com.ahad.exceptions;

// Resource not found (User, Job, Education, etc.)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
