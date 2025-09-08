package com.ahad.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "education")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    @NotBlank(message = "Institution name is required")
    @Column(nullable = false, length = 150)
    private String institutionName;

    @NotBlank(message = "Degree is required")
    @Column(nullable = false, length = 100)
    private String degree; // e.g., B.Tech, M.Sc, MBA

    @NotBlank(message = "Field of study is required")
    @Column(nullable = false, length = 100)
    private String fieldOfStudy; // e.g., Computer Science, Electrical Engineering

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private boolean currentlyStudying; // true = still enrolled

    private String grade; // e.g., GPA/Percentage

    private String description; // Additional details, achievements, coursework

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_information_id", nullable = false)
    private UserInformation userInformation;
}
