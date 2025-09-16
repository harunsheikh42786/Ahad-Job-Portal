package com.ahad.dto.profile;

import java.util.List;
import java.util.UUID;

import com.ahad.dto.imports.JobPostForUserDTO;
import com.ahad.enums.JobStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInformationProfileDTO {

    private UUID id;
    private String headline;
    private String portfolio;
    private JobStatus jobStatus;

    private List<EducationProfileDTO> educations;
    private List<JobHistoryProfileDTO> jobHistories;
    private List<AchievementProfileDTO> achievements;
    private AddressProfileDTO address;
    private List<JobPostForUserDTO> appliedJobs;
}
