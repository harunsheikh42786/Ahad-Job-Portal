package com.ahad.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDTO {
    private UUID id;
    private String path;
    private String landmark;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
