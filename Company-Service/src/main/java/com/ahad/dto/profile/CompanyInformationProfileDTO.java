package com.ahad.dto.profile;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.ahad.dto.imports.JobPostForCompanyDTO;
import com.ahad.dto.imports.UserSearchForCompanyhDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInformationProfileDTO {
    private UUID id;

    private String websiteLink;

    private LocalDate registrationDate;

    private String registrationId;

    private String headline;

    private List<CompanyAddressProfileDTO> addresses;
    private List<UserSearchForCompanyhDTO> employers;
    private List<JobPostForCompanyDTO> jobPosts;

    private String description;

}
