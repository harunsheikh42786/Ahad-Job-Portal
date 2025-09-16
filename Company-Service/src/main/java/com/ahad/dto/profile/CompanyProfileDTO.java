package com.ahad.dto.profile;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyProfileDTO {
    private UUID id;
    private String name;
    private String email;
    private String contactNumber;

    private CompanyInformationProfileDTO companyInformationDTO;

    private boolean open;
}
