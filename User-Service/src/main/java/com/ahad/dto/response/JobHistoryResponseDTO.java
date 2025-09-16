package com.ahad.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

import com.ahad.dto.imports.CompanySearchDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobHistoryResponseDTO {
    private UUID id;
    private String jobTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean currentJob;
    private CompanySearchDTO companyDTO;
}
