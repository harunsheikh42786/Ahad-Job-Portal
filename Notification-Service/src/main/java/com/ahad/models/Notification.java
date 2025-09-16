package com.ahad.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.ahad.enums.NotificationTarget;
import com.ahad.enums.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private NotificationType type; // INFO, JOB_UPDATE, APPLICATION_STATUS, MESSAGE

    @Enumerated(EnumType.STRING)
    private NotificationTarget target; // USER or COMPANY

    private String title; // Short heading
    private String message; // Full message content

    private LocalDateTime createdAt;

    private boolean isCompany = false; // Company or User
    private boolean isRead = false; // User ने देखा या नहीं
    private boolean isBroadcast = false; // सबको भेजा या specific user/company को

    private UUID receiverId; // किस user or company को भेजना है

    // createdAt ko auto set karne ke liye
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
