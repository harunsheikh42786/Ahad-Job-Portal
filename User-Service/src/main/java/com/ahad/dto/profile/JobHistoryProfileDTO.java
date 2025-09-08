package com.ahad.dto.profile;

import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobHistoryProfileDTO {
    private UUID id;
    private String jobTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean currentJob;
}
