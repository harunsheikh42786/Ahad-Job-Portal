package com.ahad.dto.update;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobHistoryUpdateDTO {

    private UUID companyId;

    private String jobTitle;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean currentJob; // nullable → update only if provided

}
