package com.ahad.dto.exports;

import java.time.LocalDateTime;

import com.ahad.dto.imports.CompanyForJobDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostForUserDTO {

    private String id;
    private String title;
    private String location;
    private LocalDateTime postedAt;
    private LocalDateTime expiresAt;
    private Double minSalary;
    private Double maxSalary;
    private boolean active;
    private CompanyForJobDTO company;
    private JobApplicationForUserDTO application;

}
