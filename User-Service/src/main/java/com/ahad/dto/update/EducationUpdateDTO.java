package com.ahad.dto.update;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationUpdateDTO {

    private UUID id; // optional, mostly not needed for PATCH

    private String institutionName;

    private String degree;

    private String fieldOfStudy;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean currentlyStudying; // Boolean instead of boolean (null = ignore in PATCH)

    private String grade;

    private String description;

    private UUID userInformationId; // optional, normally set in service
}
