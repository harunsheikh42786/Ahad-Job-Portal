package com.ahad.dto.imports;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationForCompanyDTO {
    private String id;
    private String jobPostId;
    private String applicantId;
    private String resumeLink;
    private String status; // e.g., "APPLIED", "REVIEWED", "INTERVIEW_SCHEDULED", "OFFERED", "REJECTED"
    private String appliedAt; // ISO 8601 date-time string
    private String updatedAt; // ISO 8601 date-time string

}
