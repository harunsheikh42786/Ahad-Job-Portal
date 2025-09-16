package com.ahad.messages;

public class NotificationMessages {
    // === Job Notifications ===
    public static final String NEW_JOB_POSTED = "New job posted matching your preferences for the role of %s at %s.";
    public static final String JOB_APPLICATION_RECEIVED = "Your application for %s has been successfully submitted.";
    public static final String JOB_APPLICATION_STATUS = "Your application for %s has been %s.";

    // === Company Notifications ===
    public static final String NEW_APPLICATION = "A new application has been submitted for the role of %s.";
    public static final String CANDIDATE_STATUS_UPDATE = "The status for candidate %s has been updated to %s.";

    // === General Info ===
    public static final String SYSTEM_ALERT = "System alert: %s";
    public static final String MESSAGE_RECEIVED = "You have a new message from %s.";

    // === Utility Methods for Dynamic Messages ===
    public static String newJobPosted(String role, String company) {
        return String.format(NEW_JOB_POSTED, role, company);
    }

    public static String jobApplicationReceived(String role) {
        return String.format(JOB_APPLICATION_RECEIVED, role);
    }

    public static String jobApplicationStatus(String role, String status) {
        return String.format(JOB_APPLICATION_STATUS, role, status);
    }

    public static String newApplication(String role) {
        return String.format(NEW_APPLICATION, role);
    }

    public static String candidateStatusUpdate(String candidateName, String status) {
        return String.format(CANDIDATE_STATUS_UPDATE, candidateName, status);
    }

    public static String systemAlert(String detail) {
        return String.format(SYSTEM_ALERT, detail);
    }

    public static String messageReceived(String sender) {
        return String.format(MESSAGE_RECEIVED, sender);
    }
}
