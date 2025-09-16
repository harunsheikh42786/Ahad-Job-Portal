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
public class CompanyAddressProfileDTO {
    private UUID id;

    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
}
