package com.ahad.dto.exports;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostForCompanyDTO {

    private String id;
    private String title;
    private String location;
    private LocalDateTime postedAt;
    private LocalDateTime expiresAt;
    private Double minSalary;
    private Double maxSalary;
    private boolean active;
    private List<JobApplicationForCompanyDTO> applications;

}
