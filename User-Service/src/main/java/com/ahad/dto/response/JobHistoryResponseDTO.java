package com.ahad.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobHistoryResponseDTO {
    private UUID id;
    private UUID companyId;
    private String jobTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean currentJob;
}
