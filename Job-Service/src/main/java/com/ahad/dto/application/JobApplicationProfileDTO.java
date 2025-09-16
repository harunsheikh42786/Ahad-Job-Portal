package com.ahad.dto.application;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ahad.enums.ApplicationStatus;

import lombok.*;;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationProfileDTO {

    // Resume link or file path
    private String resumeUrl;

    private ApplicationStatus status; // APPLIED, SHORTLISTED, INTERVIEW, HIRED, REJECTED

    private LocalDateTime appliedAt;

    private LocalDateTime updatedAt;

    private UUID jobPostId;

    private UUID applicantId;

}
