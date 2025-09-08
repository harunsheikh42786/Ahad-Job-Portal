package com.ahad.enums;

public enum NotificationType {
    // 🔹 Job Related
    JOB_CREATED("A new job has been created by {company}"),
    JOB_UPDATED("Job details updated by {company}"),
    JOB_DELETED("Job removed by {company}"),

    // 🔹 Application Related
    APPLICATION_SUBMITTED("{user} has applied for a job at {company}"),
    APPLICATION_ACCEPTED("Your application has been accepted by {company}"),
    APPLICATION_REJECTED("Your application has been rejected by {company}"),
    APPLICATION_SHORTLISTED("Your application has been shortlisted by {company}"),

    // 🔹 Account / Security Related
    PASSWORD_CHANGED("Your password has been changed successfully"),
    LOGIN_SUCCESS("Login successful"),
    LOGIN_FAILED("Login attempt failed"),
    ACCOUNT_LOCKED("Your account has been locked due to multiple failed login attempts"),
    ACCOUNT_CREATED("Your account has been created successfully"),

    // 🔹 Messaging / Communication
    NEW_MESSAGE("You have received a new message from {company}"),
    INTERVIEW_SCHEDULED("An interview has been scheduled by {company}"),
    INTERVIEW_RESCHEDULED("An interview has been rescheduled by {company}"),
    INTERVIEW_CANCELLED("An interview has been cancelled by {company}");

    private final String messageTemplate;

    NotificationType(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    /**
     * Replace placeholders in the template with actual values
     */
    public String formatMessage(String user, String company, String job) {
        return messageTemplate
                .replace("{user}", user != null ? user : "")
                .replace("{company}", company != null ? company : "")
                .replace("{job}", job != null ? job : "");
    }
}