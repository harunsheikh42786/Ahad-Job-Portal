package com.ahad.dto.exports;

import com.ahad.dto.imports.UserForJobDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationForCompanyDTO {

    private String id;
    private UserForJobDTO applicant;
    private String resumeUrl;
    private String status; // e.g., "APPLIED", "REVIEWED", "INTERVIEW_SCHEDULED", "OFFERED", "REJECTED"
    private String appliedAt; // ISO 8601 date-time string
    private String updatedAt; // ISO 8601 date-time string

}
