package com.ahad.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import com.ahad.enums.ExperienceLevel;
import com.ahad.enums.JobType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "job_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPost {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    @NotBlank(message = "Job title is required")
    @Size(max = 100, message = "Job title cannot exceed 100 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Job description is required and should be upto 3000 chars.")
    @Column(nullable = false, columnDefinition = "TEXT", length = 3000)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Job type is required")
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Experience level is required")
    private ExperienceLevel experienceLevel;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @PositiveOrZero(message = "Minimum salary must be zero or greater")
    private Double minSalary;

    @PositiveOrZero(message = "Maximum salary must be zero or greater")
    private Double maxSalary;

    private LocalDateTime postedAt;

    @Future(message = "Date should be in future")
    private LocalDateTime expiresAt;

    private boolean isActive;

    // ✅ Foreign Keys only
    @Column(name = "company_id", nullable = false, updatable = false)
    private UUID companyId;

    @Column(name = "recruiter_id", nullable = false, updatable = false)
    private UUID recruiterId;

    // Job Applications
    @OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplication> applications;
}
