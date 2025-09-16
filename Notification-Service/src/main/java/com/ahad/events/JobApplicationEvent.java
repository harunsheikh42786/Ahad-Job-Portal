package com.ahad.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Event to be published whenever a job application is created or updated.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationEvent {

    private UUID applicationId;
    private UUID jobPostId;
    private String jobTitle;
    private UUID applicantId; // User who applied
    private UUID companyId;

}
