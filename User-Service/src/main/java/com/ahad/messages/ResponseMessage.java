package com.ahad.messages;

public class ResponseMessage {

    public static final String SUCCESS_VERIFICATION = "verification is success";
    public static final String FAILED_VERIFICATION = "Invalid email or password";
    // 🔹 Common
    public static final String CREATED = "created successfully";
    public static final String UPDATED = "updated successfully";
    public static final String FETCHED = "fetched successfully";
    public static final String DELETED = "deleted successfully";
    public static final String FAILED = "Operation failed";
    public static final String NOT_FOUND = "not found";
    public static final String NO_DATA = "No data available for ";

    // 🔹 Resource Errors
    public static final String RESOURCE_NOT_FOUND = "Requested resource not found";
    public static final String DUPLICATE_RESOURCE = "Resource already exists";
    public static final String ID_NOT_FOUND = "not found with this id :";
    public static final String EMAIL_NOT_FOUND = "not found with this email :";
    public static final String DUPLICATE_EMAIL = "Email already existed";
    public static final String DUPLICATE_CONTACT_NUMBER = "Contact number already existed";

    // 🔹 Request Errors
    public static final String BAD_REQUEST = "Invalid request data";
    public static final String UNAUTHORIZED = "You are not authorized to perform this action";

    // 🔹 System / Processing Errors
    public static final String MAPPING_FAILED = "Failed to map the request/response";
    public static final String GENERIC_ERROR = "An unexpected error occurred";
    public static final String DATABASE_ERROR = "Database operation failed";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
}
