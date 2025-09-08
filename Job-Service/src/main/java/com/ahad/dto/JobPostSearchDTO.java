package com.ahad.dto;

import com.ahad.enums.ExperienceLevel;
import com.ahad.enums.JobType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostSearchDTO {
    private UUID id;
    private String title;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private LocalDateTime expiresAt;
    private String location;
    private String companyName;
}
