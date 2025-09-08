package com.ahad.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import com.ahad.enums.AchievementType;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "achievements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Achievement {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false, length = 150)
    private String title; // e.g., "Hackathon Winner"

    private String description; // details of the achievement

    private LocalDate date; // when achieved

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private AchievementType type;
    // e.g., EDUCATION, JOB, PERSONAL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_info_id", nullable = false)
    private UserInformation userInformation;
}
