package com.ahad.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.ahad.enums.NotificationTarget;
import com.ahad.enums.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;
    private NotificationType type; // INFO, JOB_UPDATE, APPLICATION_STATUS, MESSAGE आदि
    private NotificationTarget target; // User or Company
    private String title; // Short heading
    private String message; // Full message content
    private LocalDateTime createdAt;
    private boolean isRead = false; // User ने देखा या नहीं
    private UUID referenceId; // किस user or company को भेजना है
}
