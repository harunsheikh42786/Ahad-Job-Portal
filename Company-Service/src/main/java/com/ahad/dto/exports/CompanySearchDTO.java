package com.ahad.dto.exports;

import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySearchDTO {
    private UUID id;
    private String name;
    private String headline;
    private String location;
}
