package com.ahad.dto;

import java.util.Set;
import java.util.UUID;

import com.ahad.dto.external.JobPostDTO;
import com.ahad.dto.external.UserSearchDTO;
import com.ahad.models.CompanyAddress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyProfileDTO {
    private UUID id;
    private String name;
    private String email;
    private String contactNumber;
    private String websiteLink;
    private String registrationDate;
    private String registrationId;
    private String headline;
    private String description;

    // relations
    private Set<UserSearchDTO> employers; // only lightweight info
    private Set<JobPostDTO> jobPosts;
    private Set<CompanyAddress> addresses;

    private boolean open;
}
