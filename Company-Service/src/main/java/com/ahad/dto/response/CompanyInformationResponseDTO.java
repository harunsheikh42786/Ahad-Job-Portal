package com.ahad.dto.response;

import java.time.LocalDate;
import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInformationResponseDTO {
    private UUID id; // usually auto-generated, no validation needed

    private String websiteLink;

    private LocalDate registrationDate;

    private String registrationId;

    private String headline;

}
