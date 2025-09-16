package com.ahad.dto.imports;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationForUserDTO {
    private String id;
    private String resumeUrl;
    private String status; // e.g., "APPLIED", "REVIEWED", "INTERVIEW_SCHEDULED", "OFFERED", "REJECTED"
    private String appliedAt; // ISO 8601 date-time string
    private String updatedAt; // ISO 8601 date-time string
}
