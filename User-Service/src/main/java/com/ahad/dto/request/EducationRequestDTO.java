package com.ahad.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationRequestDTO {
    @NotBlank(message = "Institution name is required")
    @Column(nullable = false, length = 150)
    private String institutionName;

    @NotBlank(message = "Degree is required")
    @Column(nullable = false, length = 100)
    private String degree; // e.g., B.Tech, M.Sc, MBA

    @NotBlank(message = "Field of study is required")
    @Column(nullable = false, length = 100)
    private String fieldOfStudy; // e.g., Computer Science, Electrical Engineering

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private boolean currentlyStudying; // true = still enrolled

    private String grade; // e.g., GPA/Percentage

    private String description; // Additional details, achievements, coursework

    @Column(nullable = false)
    private UUID userInformationId;
}
