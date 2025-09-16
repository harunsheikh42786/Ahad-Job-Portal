package com.ahad.dto.application;

import com.ahad.enums.ApplicationStatus;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationRequestDTO {

    // Resume link or file path
    @NotBlank(message = "Resume URL is required")
    @Size(max = 300, message = "Resume URL cannot exceed 300 characters")
    private String resumeUrl;

    // @NotNull(message = "Application status is required")
    private ApplicationStatus status; // APPLIED, SHORTLISTED, INTERVIEW, HIRED, REJECTED

    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;
    // Job Post for which application is made

    @NotNull(message = "Job Post ID is required")
    private UUID jobPostId;

    @NotNull(message = "Applicant ID is required")
    private UUID applicantId;

}
