package com.ahad.dto.profile;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationProfileDTO {

    private UUID id;
    private String institutionName;

    private String degree; // e.g., B.Tech, M.Sc, MBA

    private String fieldOfStudy; // e.g., Computer Science, Electrical Engineering

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean currentlyStudying; // true = still enrolled

    private String grade; // e.g., GPA/Percentage

    private String description; // Additional details, achievements, coursework

}
