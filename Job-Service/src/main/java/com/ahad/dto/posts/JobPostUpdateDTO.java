package com.ahad.dto.posts;

import com.ahad.enums.ExperienceLevel;
import com.ahad.enums.JobType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostUpdateDTO {
    private String title;
    private String description;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private String location;
    private Double minSalary;
    private Double maxSalary;
    private LocalDateTime expiresAt;
    private Boolean active;
    private UUID companyId;
}
