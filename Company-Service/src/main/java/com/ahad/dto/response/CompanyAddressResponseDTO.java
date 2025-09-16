package com.ahad.dto.response;

import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyAddressResponseDTO {
    private UUID id; // usually auto-generated, so no validation

    private String street;

    private String city;

    private String state;

    private String country;

    private String postalCode;
}
