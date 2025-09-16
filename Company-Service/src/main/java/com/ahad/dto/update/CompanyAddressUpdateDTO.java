package com.ahad.dto.update;

import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyAddressUpdateDTO {
    private UUID id; // usually auto-generated, so no validation

    private String street;

    private String city;

    private String state;

    private String country;

    private String postalCode;
}
