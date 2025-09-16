package com.ahad.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import com.ahad.enums.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "job_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    // Job Post for which application is made
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_post_id", nullable = false)
    private JobPost jobPost;

    // Resume link or file path
    @NotBlank(message = "Resume URL is required")
    @Size(max = 300, message = "Resume URL cannot exceed 300 characters")
    private String resumeUrl;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Application status is required")
    private ApplicationStatus status; // APPLIED, SHORTLISTED, INTERVIEW, HIRED, REJECTED

    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;

    private UUID applicantId;

    @PrePersist
    public void onCreate() {
        this.appliedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = ApplicationStatus.APPLIED;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
