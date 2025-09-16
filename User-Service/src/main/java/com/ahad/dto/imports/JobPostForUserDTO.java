package com.ahad.dto.imports;

import java.time.LocalDateTime;

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
    private CompanySearchDTO company;
    private JobApplicationForUserDTO application;

}
