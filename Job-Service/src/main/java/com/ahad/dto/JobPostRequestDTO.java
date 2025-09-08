package com.ahad.dto;

import com.ahad.enums.ExperienceLevel;
import com.ahad.enums.JobType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostRequestDTO {

    @NotBlank(message = "Job title is required")
    @Size(max = 100, message = "Job title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Job description is required and should be upto 3000 chars.")
    @Size(max = 3000, message = "Job description cannot exceed 3000 characters")
    private String description;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    @NotNull(message = "Experience level is required")
    private ExperienceLevel experienceLevel;

    @NotBlank(message = "Location is required")
    private String location;

    @PositiveOrZero(message = "Minimum salary must be zero or greater")
    private Double minSalary;

    @PositiveOrZero(message = "Maximum salary must be zero or greater")
    private Double maxSalary;

    @NotNull(message = "Company ID can't be empty")
    private UUID companyId;
    @NotNull(message = "Recruiter ID can't be empty")
    private UUID recruiterId;

    private LocalDateTime expiresAt; // optional
}
